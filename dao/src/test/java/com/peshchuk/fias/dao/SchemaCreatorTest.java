package com.peshchuk.fias.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Ruslan Peshchuk(peshrus@gmail.com)
 */
public class SchemaCreatorTest {
	private SchemaCreator schemaCreator;

	@Before
	public void setUp() {
		schemaCreator = new SchemaCreator();
	}

	@Test
	public void testCreateSchema_OK() throws SQLException, IOException {
		try (final Connection connection =
				     DriverManager.getConnection("jdbc:h2:mem:SchemaCreatorTest;MODE=PostgreSQL;LOCK_TIMEOUT=20000;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=TRUE")) {
			final boolean autoCommit = connection.getAutoCommit();
			try {
				connection.setAutoCommit(false);

				assertTrue(schemaCreator.createSchema(connection));
				assertFalse(schemaCreator.createSchema(connection));
			} finally {
				connection.setAutoCommit(autoCommit);
			}
		}
	}
}