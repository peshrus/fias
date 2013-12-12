package com.peshchuk.fias.xml.saver;

import java.lang.reflect.Field;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.datatype.XMLGregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;

/**
 * @author Ruslan Peshchuk (peshrus@gmail.com)
 */
public class EntitySaver implements AutoCloseable {
	private static final Logger LOGGER = LoggerFactory.getLogger(EntitySaver.class);

	private final Connection connection;
	private final boolean supportsBatchUpdates;
	private final boolean defaultAutoCommit;
	private final Map<Class<?>, PreparedStatement> preparedStatements;
	private final Map<Class<?>, Field[]> classFields;

	public EntitySaver(Connection connection, Set<Class<?>> jaxbClasses) throws SQLException {
		this.connection = connection;

		this.supportsBatchUpdates = connection.getMetaData().supportsBatchUpdates();
		LOGGER.info("supportsBatchUpdates: {}", supportsBatchUpdates);

		this.defaultAutoCommit = connection.getAutoCommit();
		LOGGER.info("defaultAutoCommit: {}", defaultAutoCommit);
		connection.setAutoCommit(false);

		final int jaxbClassesSize = jaxbClasses.size();
		this.preparedStatements = Maps.newHashMapWithExpectedSize(jaxbClassesSize);
		this.classFields = Maps.newHashMapWithExpectedSize(jaxbClassesSize);

		for (Class<?> xmlRootClass : jaxbClasses) {
			for (Class<?> entityClass : xmlRootClass.getClasses()) {
				final Field[] entityFields = entityClass.getDeclaredFields();
				classFields.put(entityClass, entityFields);
			}
		}
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

	private PreparedStatement getPreparedStatement(Object entity) throws SQLException {
		final Class<?> entityClass = entity.getClass();

		if (!preparedStatements.containsKey(entityClass)) {
			final Field[] entityFields = classFields.get(entityClass);

			final String[] fieldNames = new String[entityFields.length];
			final String[] queryParams = new String[entityFields.length];
			int pos = 0;
			for (Field entityField : entityFields) {
				entityField.setAccessible(true);

				final XmlAttribute xmlAttribute = entityField.getAnnotation(XmlAttribute.class);
				fieldNames[pos] = xmlAttribute.name();
				queryParams[pos] = "?";
				++pos;
			}
			final String sqlFields = Arrays.toString(fieldNames).replace('[', '(').replace(']', ')');
			final String sqlParams = Arrays.toString(queryParams).replace('[', '(').replace(']', ')');

			final String sql = "INSERT INTO " + entityClass.getSimpleName().toUpperCase() + sqlFields + " VALUES" + sqlParams;
			LOGGER.info("{} SQL: {}", entityClass.getSimpleName(), sql);
			preparedStatements.put(entityClass, connection.prepareStatement(sql));
		}

		return preparedStatements.get(entityClass);
	}

	private void setParameters(PreparedStatement preparedStatement, Object entity) throws
	                                                                               IllegalAccessException,
	                                                                               SQLException {
		final Field[] fields = classFields.get(entity.getClass());

		int i = 1;
		for (Field field : fields) {
			Object value = field.get(entity);

			if (value != null) {
				if (XMLGregorianCalendar.class.isAssignableFrom(field.getType())) {
					value = XMLGregorianCalendar.class.cast(value).toGregorianCalendar().getTime();
				}

				if (BigInteger.class.isAssignableFrom(field.getType())) {
					value = BigInteger.class.cast(value).intValue();
				}
			}

			preparedStatement.setObject(i++, value);
		}
	}

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

	@Override
	public void close() throws SQLException {
		for (PreparedStatement preparedStatement : preparedStatements.values()) {
			try {
				preparedStatement.close();
			} catch (Throwable throwable) {
				LOGGER.warn(throwable.toString(), throwable);
			}
		}

		connection.setAutoCommit(defaultAutoCommit);
	}
}