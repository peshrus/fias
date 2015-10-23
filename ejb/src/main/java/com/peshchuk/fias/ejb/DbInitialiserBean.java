package com.peshchuk.fias.ejb;

import com.peshchuk.fias.dao.ConstraintsInitiator;
import com.peshchuk.fias.dao.EntityDeleter;
import com.peshchuk.fias.dao.EntityProcessor;
import com.peshchuk.fias.dao.EntitySaver;
import com.peshchuk.fias.dao.SchemaCreator;
import com.peshchuk.fias.downloader.FiasRarSoapDownloader;
import com.peshchuk.fias.xml.saver.BatchProcessor;
import com.peshchuk.fias.xml.saver.FiasXmlProcessor;
import com.peshchuk.fias.xml.saver.ProcessingAssistant;
import com.peshchuk.fias.xml.saver.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Set;

/**
 * @author Ruslan Peshchuk(peshrus@gmail.com)
 */
@Startup
@Singleton
@TransactionManagement(TransactionManagementType.BEAN)
public class DbInitialiserBean {
	private static final Logger LOGGER = LoggerFactory.getLogger(DbInitialiserBean.class);

	@SuppressWarnings("unused")
	@Resource
	private DataSource fiasDs;

	@SuppressWarnings("unused")
	@PostConstruct
	public void init() throws Exception {
		boolean schemaCreated = createSchema();

		if (schemaCreated) {
//			final File fiasRarFile = new File("C:\\Users\\rupe0413\\AppData\\Local\\Temp\\fias_xml_1960655428330468346.rar");
			final File fiasRarFile = File.createTempFile("fias_xml_", ".rar");
			final FiasRarSoapDownloader downloader = new FiasRarSoapDownloader(fiasRarFile, false);
			downloader.download();
			final Set<Class<?>> jaxbClasses = Util.getJaxbClasses();

			try (final Connection connection = fiasDs.getConnection()) {
				final boolean autoCommit = connection.getAutoCommit();

				try {
					connection.setAutoCommit(false);

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
											                     entitySaver.commit();
										                     } catch (SQLException e) {
											                     entitySaver.rollback();
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
											                     entityDeleter.commit();
										                     } catch (SQLException e) {
											                     entityDeleter.rollback();
											                     throw e;
										                     }
									                     }
								                     });

						saver.process(new ProcessingAssistant() {
							@Override
							public void createConstraints() throws Exception {
								LOGGER.info("Add Constraints");
								ConstraintsInitiator.addConstraints(connection);
							}

							@Override
							public void finish() {
								LOGGER.info("Finish Processing");
							}
						});
					}

				} finally {
					connection.setAutoCommit(autoCommit);
				}
			} catch (SQLException e) {
				for (Throwable ex : e) {
					LOGGER.error(ex.getLocalizedMessage());
				}

				throw e;
			}
		} else {
			LOGGER.info("FIAS DB Schema already exists");
		}
	}

	private boolean createSchema() throws SQLException, IOException {
		boolean schemaCreated = false;

		try (final Connection connection = fiasDs.getConnection()) {
			final boolean autoCommit = connection.getAutoCommit();

			try {
				connection.setAutoCommit(false);
				schemaCreated = SchemaCreator.createSchema(connection);
			} finally {
				connection.setAutoCommit(autoCommit);
			}
		}

		return schemaCreated;
	}
}