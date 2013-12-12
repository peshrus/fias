package com.peshchuk.fias.xml.saver;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Ruslan Peshchuk (peshrus@gmail.com)
 */
public class FiasXmlSaverTest {
	private FiasXmlSaver saver;
	private Connection connection;
	private EntitySaver entitySaver;

	@Before
	public void setUp() throws URISyntaxException, JAXBException, SQLException {
		final URL fiasRarUrl = FiasXmlSaverTest.class.getClassLoader().getResource("fias_delta_xml.rar");
		assertNotNull(fiasRarUrl);

		connection = DriverManager.getConnection("jdbc:h2:mem:FiasXmlSaverTest;DB_CLOSE_DELAY=-1;MODE=PostgreSQL;INIT=RUNSCRIPT FROM 'classpath:create.sql'");
		entitySaver = new EntitySaver(connection);
		saver = new FiasXmlSaver(new File(new URI(fiasRarUrl.toString()).getPath()), 1000, entitySaver);
	}

	@After
	public void tearDown() throws SQLException {
		entitySaver.close();
		connection.close();
	}

	@Test
	public void testSave_Ok() throws Exception {
		saver.save();
		assertEquals(4210, entitySaver.getSavedCount());
	}
}