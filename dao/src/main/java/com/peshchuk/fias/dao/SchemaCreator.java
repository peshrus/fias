package com.peshchuk.fias.dao;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Ruslan Peshchuk(peshrus@gmail.com)
 */
public class SchemaCreator {
	public boolean createSchema(Connection connection) throws SQLException, IOException {
		boolean result = false;
		final boolean fiasSchemaExists = doesFiasSchemaExist(connection.getMetaData());

		if (!fiasSchemaExists) {
			final BufferedInputStream scriptStream =
					new BufferedInputStream(SchemaCreator.class.getResourceAsStream("/create.sql"), Short.MAX_VALUE);

			ScriptExecutor.executeScript(connection, scriptStream);
			result = true;
		}

		return result;
	}

	private boolean doesFiasSchemaExist(DatabaseMetaData metaData) throws SQLException {
		try (final ResultSet tables = metaData.getTables("", "", "OBJECT", null)) {
			return tables.next();
		}
	}

}