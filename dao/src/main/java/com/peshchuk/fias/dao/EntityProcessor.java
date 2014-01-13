package com.peshchuk.fias.dao;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;

/**
 * @author Ruslan Peshchuk (peshrus@gmail.com)
 */
public abstract class EntityProcessor implements AutoCloseable {
	private static final Logger LOGGER = LoggerFactory.getLogger(EntityProcessor.class);

	private final Connection connection;
	private final boolean supportsBatchUpdates;
	private final Map<Class<?>, PreparedStatement> preparedStatements;
	private final boolean storesLowerCaseIdentifiers;

	public EntityProcessor(Connection connection, Set<Class<?>> jaxbClasses) throws SQLException {
		final DatabaseMetaData metaData = connection.getMetaData();
		this.connection = connection;
		this.storesLowerCaseIdentifiers = metaData.storesLowerCaseIdentifiers();
		LOGGER.info("storesLowerCaseIdentifiers: {}", storesLowerCaseIdentifiers);
		this.supportsBatchUpdates = metaData.supportsBatchUpdates();
		LOGGER.info("supportsBatchUpdates: {}", supportsBatchUpdates);

		final int jaxbClassesSize = jaxbClasses.size();
		this.preparedStatements = Maps.newHashMapWithExpectedSize(jaxbClassesSize);
	}

	protected Connection getConnection() {
		return connection;
	}

	protected Map<Class<?>, PreparedStatement> getPreparedStatements() {
		return preparedStatements;
	}

	protected String transformIdentifier(String identifier) {
		String result;

		if (storesLowerCaseIdentifiers) {
			result = identifier.toLowerCase();
		} else {
			result = identifier.toUpperCase();
		}

		return result;
	}

	public void addBatch(Object entity) throws SQLException, IllegalAccessException {
		final PreparedStatement preparedStatement = getPreparedStatement(entity);
		setParameters(preparedStatement, entity);

		if (supportsBatchUpdates) {
			preparedStatement.addBatch();
		} else {
			preparedStatement.executeUpdate();
		}
	}

	protected abstract PreparedStatement getPreparedStatement(Object entity) throws SQLException;

	protected abstract void setParameters(PreparedStatement preparedStatement, Object entity) throws
	                                                                                          IllegalAccessException,
	                                                                                          SQLException;

	public void executeBatch() throws SQLException {
		if (supportsBatchUpdates) {
			for (PreparedStatement preparedStatement : preparedStatements.values()) {
				try {
					preparedStatement.executeBatch();
				} catch (Throwable throwable) {
					LOGGER.error(throwable.toString(), throwable);
				}
			}
		}
	}

	public void commit() throws SQLException {
		connection.commit();
	}

	public void rollback() throws SQLException {
		connection.rollback();
	}

	@Override
	public void close() throws SQLException {
		for (PreparedStatement preparedStatement : preparedStatements.values()) {
			try {
				preparedStatement.close();
			} catch (Throwable throwable) {
				LOGGER.warn(throwable.toString(), throwable);
			}
		}
	}
}