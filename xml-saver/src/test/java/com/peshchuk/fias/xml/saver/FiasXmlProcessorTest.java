package com.peshchuk.fias.xml.saver;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.xml.bind.JAXBException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.peshchuk.fias.dao.EntityDeleter;
import com.peshchuk.fias.dao.EntityProcessor;
import com.peshchuk.fias.dao.EntitySaver;
import com.peshchuk.fias.dao.SchemaCreator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Ruslan Peshchuk (peshrus@gmail.com)
 */
public class FiasXmlProcessorTest {
	private FiasXmlProcessor saver;
	private Connection connection;
	private boolean autoCommit;
	private EntityProcessor entitySaver;
	private EntityProcessor entityDeleter;
	private int savedCount;
	private int deletedCount;

	@Before
	public void setUp() throws URISyntaxException, JAXBException, SQLException, IOException {
		final URL fiasRarUrl = FiasXmlProcessorTest.class.getClassLoader().getResource("fias_delta_xml.rar");
		assertNotNull(fiasRarUrl);

		connection =
				DriverManager.getConnection(
						"jdbc:h2:mem:FiasXmlProcessorTest;MODE=PostgreSQL;LOCK_TIMEOUT=20000;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=TRUE");
		autoCommit = connection.getAutoCommit();
		connection.setAutoCommit(false);

		assertTrue(SchemaCreator.createSchema(connection));

		final Set<Class<?>> jaxbClasses = Util.getJaxbClasses();
		entitySaver = new EntitySaver(connection, jaxbClasses);
		entityDeleter = new EntityDeleter(connection, jaxbClasses);
		savedCount = 0;
		deletedCount = 0;
		saver = new FiasXmlProcessor(new File(new URI(fiasRarUrl.toString()).getPath()),
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
					                             savedCount += batch.size();
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
					                             deletedCount += batch.size();
				                             } catch (SQLException e) {
					                             connection.rollback();
					                             throw e;
				                             }
			                             }
		                             });
	}

	@After
	public void tearDown() throws SQLException {
		try {
			connection.setAutoCommit(autoCommit);
			entitySaver.close();
			entityDeleter.close();
		} finally {
			connection.close();
		}
	}

	@Test
	public void testSave_Ok() throws Exception {
		final AtomicBoolean createConstraintsCalled = new AtomicBoolean(false);
		final AtomicBoolean finished = new AtomicBoolean(false);
		saver.process(new ProcessingAssistant() {
			              @Override
			              public void createConstraints() throws IOException, SQLException {
				              createConstraintsCalled.set(true);
			              }

			              @Override
			              public void finish() {
				              finished.set(true);
			              }
		              }
		);
		assertEquals(4210, savedCount);
		assertEquals(982, deletedCount);
		assertTrue(createConstraintsCalled.get());
		assertTrue(finished.get());
	}
}