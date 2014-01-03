package com.peshchuk.fias.ejb;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.peshchuk.fias.dao.ConstraintsInitiator;
import com.peshchuk.fias.dao.EntityDeleter;
import com.peshchuk.fias.dao.EntityProcessor;
import com.peshchuk.fias.dao.EntitySaver;
import com.peshchuk.fias.dao.SchemaCreator;
import com.peshchuk.fias.downloader.FiasRarSoapDownloader;
import com.peshchuk.fias.xml.saver.BatchProcessor;
import com.peshchuk.fias.xml.saver.ConstraintsCreator;
import com.peshchuk.fias.xml.saver.FiasXmlProcessor;
import com.peshchuk.fias.xml.saver.Util;

/**
 * @author Ruslan Peshchuk(peshrus@gmail.com)
 */
@Startup
@Singleton
@TransactionManagement(TransactionManagementType.BEAN)
public class DbInitialiserBean {
	private static final Logger LOGGER = LoggerFactory.getLogger(DbInitialiserBean.class);

	@Resource
	private DataSource fiasDs;

	@PostConstruct
	public void init() throws Exception {
		try (final Connection connection = fiasDs.getConnection()) {
			final boolean autoCommit = connection.getAutoCommit();

			try {
				final SchemaCreator schemaCreator = new SchemaCreator();
				final boolean schemaCreated = schemaCreator.createSchema(connection);

				if (schemaCreated) {
					final File fiasRarFile = File.createTempFile("fias_xml_", ".rar");
					final FiasRarSoapDownloader downloader = new FiasRarSoapDownloader(fiasRarFile, false);
					downloader.download();
					final Set<Class<?>> jaxbClasses = Util.getJaxbClasses();

					try (final EntityProcessor entitySaver = new EntitySaver(connection, jaxbClasses);
					     final EntityProcessor entityDeleter = new EntityDeleter(connection, jaxbClasses)) {
						final FiasXmlProcessor saver =
								new FiasXmlProcessor(fiasRarFile,
								                     jaxbClasses,
								                     1000,
								                     new BatchProcessor() {
									                     @Override
									                     public void save(Collection<?> batch) throws Exception {
										                     try {
											                     for (Object obj : batch) {
												                     entitySaver.addBatch(obj);
											                     }
											                     entitySaver.executeBatch();
											                     connection.commit();
										                     } catch (SQLException e) {
											                     connection.rollback();
											                     throw e;
										                     }
									                     }

									                     @Override
									                     public void delete(Collection<?> batch) throws Exception {
										                     try {
											                     for (Object obj : batch) {
												                     entityDeleter.addBatch(obj);
											                     }
											                     entityDeleter.executeBatch();
											                     connection.commit();
										                     } catch (SQLException e) {
											                     connection.rollback();
											                     throw e;
										                     }
									                     }
								                     });
						final ConstraintsInitiator constraintsInitiator = new ConstraintsInitiator(connection);

						saver.process(new ConstraintsCreator() {
							@Override
							public void createConstraints() throws Exception {
								constraintsInitiator.addConstraints();
							}
						});
					}
				} else {
					LOGGER.info("FIAS DB Schema already exists");
				}
			} finally {
				connection.setAutoCommit(autoCommit);
			}
		}
	}
}