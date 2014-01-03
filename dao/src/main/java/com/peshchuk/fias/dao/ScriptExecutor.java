package com.peshchuk.fias.dao;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Ruslan Peshchuk(peshrus@gmail.com)
 */
public class ScriptExecutor {
	private static final Logger LOGGER = LoggerFactory.getLogger(ScriptExecutor.class);

	public static final String SQL_COMMAND_END = ";\n"; // Unix EOL is supposed to be used in the script files
	public static final String SCRIPT_CHARSET = "utf8"; // Script files have to be in UTF-8 encoding

	public static void executeScript(Connection connection,
	                                 BufferedInputStream scriptStream) throws SQLException, IOException {
		final DatabaseMetaData metaData = connection.getMetaData();
		final boolean supportsBatchUpdates = metaData.supportsBatchUpdates();

		try (final Statement statement = connection.createStatement()) {
			final StringBuilder sqlBuilder = new StringBuilder(Short.MAX_VALUE);
			final byte[] buffer = new byte[Short.MAX_VALUE];
			int read;
			final Charset charset = Charset.forName(SCRIPT_CHARSET);

			while ((read = scriptStream.read(buffer)) > 0) {
				final String portion = new String(buffer, 0, read, charset);
				final String[] scriptParts = portion.split(SQL_COMMAND_END);

				for (int i = 0; i < scriptParts.length; i++) {
					final String scriptPart = scriptParts[i];
					sqlBuilder.append(scriptPart);

					if (i < scriptParts.length - 1 || scriptPart.endsWith(";")) { // SQL command is complete
						final String sql =
								!scriptPart.endsWith(";") ?
										sqlBuilder.toString() :
										sqlBuilder.substring(0, sqlBuilder.length() - 1);
						LOGGER.trace("{};", sql);

						if (supportsBatchUpdates) {
							statement.addBatch(sql);
						} else {
							statement.executeUpdate(sql);
						}
						sqlBuilder.delete(0, sqlBuilder.length());
					}
				}
			}

			if (supportsBatchUpdates) {
				statement.executeBatch();
			}
			connection.commit();
		} catch (SQLException e) {
			connection.rollback();
			throw e;
		} finally {
			scriptStream.close();
		}
	}
}
