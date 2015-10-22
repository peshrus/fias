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
public class ConstraintsInitiator {
	public static boolean addConstraints(Connection connection) throws IOException, SQLException {
		boolean result = false;
		final boolean fiasConstraintsExist = doesFiasConstraintsExist(connection.getMetaData());

		if (!fiasConstraintsExist) {
			final BufferedInputStream scriptStream =
					new BufferedInputStream(SchemaCreator.class.getResourceAsStream("/add-constraints.sql"), Short.MAX_VALUE);

			ScriptExecutor.executeScript(connection, scriptStream);
			result = true;
		}

		return result;
	}

	private static boolean doesFiasConstraintsExist(DatabaseMetaData metaData) throws SQLException {
		try (final ResultSet tables =
				     metaData.getPrimaryKeys("", "", metaData.storesLowerCaseIdentifiers() ? "object" : "OBJECT")) {
			return tables.next();
		}
	}
}
