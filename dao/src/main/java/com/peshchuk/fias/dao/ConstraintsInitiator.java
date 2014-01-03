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
	private final Connection connection;

	public ConstraintsInitiator(Connection connection) {
		this.connection = connection;
	}

	public boolean addConstraints() throws IOException, SQLException {
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

	private boolean doesFiasConstraintsExist(DatabaseMetaData metaData) throws SQLException {
		try (final ResultSet tables = metaData.getPrimaryKeys("", "", "OBJECT")) {
			return tables.next();
		}
	}
}
