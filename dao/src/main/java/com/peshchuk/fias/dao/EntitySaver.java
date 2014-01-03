package com.peshchuk.fias.dao;

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
 * @author Ruslan Peshchuk(peshrus@gmail.com)
 */
public class EntitySaver extends EntityProcessor {
	private static final Logger LOGGER = LoggerFactory.getLogger(EntitySaver.class);

	private final Map<Class<?>, Field[]> classFields;

	public EntitySaver(Connection connection, Set<Class<?>> jaxbClasses) throws SQLException {
		super(connection, jaxbClasses);

		this.classFields = Maps.newHashMapWithExpectedSize(jaxbClasses.size());

		for (Class<?> xmlRootClass : jaxbClasses) {
			for (Class<?> entityClass : xmlRootClass.getClasses()) {
				final Field[] entityFields = entityClass.getDeclaredFields();
				this.classFields.put(entityClass, entityFields);
			}
		}
	}

	protected PreparedStatement getPreparedStatement(Object entity) throws SQLException {
		final Class<?> entityClass = entity.getClass();

		if (!getPreparedStatements().containsKey(entityClass)) {
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
			LOGGER.info("{} Save SQL: {}", entityClass.getSimpleName(), sql);
			getPreparedStatements().put(entityClass, getConnection().prepareStatement(sql));
		}

		return getPreparedStatements().get(entityClass);
	}

	protected void setParameters(PreparedStatement preparedStatement, Object entity) throws
	                                                                                 IllegalAccessException,
	                                                                                 SQLException {
		final Field[] fields = classFields.get(entity.getClass());

		int paramIndex = 1;
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

			preparedStatement.setObject(paramIndex++, value);
		}
	}
}
