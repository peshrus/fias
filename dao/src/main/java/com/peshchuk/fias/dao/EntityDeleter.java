package com.peshchuk.fias.dao;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.annotation.XmlAttribute;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;

/**
 * @author Ruslan Peshchuk(peshrus@gmail.com)
 */
public class EntityDeleter extends EntityProcessor {
	private static final Logger LOGGER = LoggerFactory.getLogger(EntityDeleter.class);

	private final Map<Class<?>, Field> classIdFields;

	public EntityDeleter(Connection connection, Set<Class<?>> jaxbClasses) throws SQLException {
		super(connection, jaxbClasses);

		this.classIdFields = Maps.newHashMapWithExpectedSize(jaxbClasses.size());

		for (Class<?> xmlRootClass : jaxbClasses) {
			for (Class<?> entityClass : xmlRootClass.getClasses()) {
				final String classSimpleName = entityClass.getSimpleName();

				switch (classSimpleName) {
					case "Object": // AS_DEL_ADDROBJ
						rememberIdField(entityClass, "aoid");
						break;
					case "House": // AS_DEL_HOUSE
						rememberIdField(entityClass, "houseid");
						break;
					case "HouseInterval": // AS_DEL_HOUSEINT
						rememberIdField(entityClass, "houseintid");
						break;
					case "Landmark": // AS_DEL_LANDMARK
						rememberIdField(entityClass, "landid");
						break;
					case "NormativeDocument": // AS_DEL_NORMDOC
						rememberIdField(entityClass, "normdocid");
						break;
				}
			}
		}
	}

	private void rememberIdField(Class<?> entityClass, String idFieldName) {
		final Field[] entityFields = entityClass.getDeclaredFields();

		for (Field entityField : entityFields) {
			if (entityField.getName().equals(idFieldName)) {
				entityField.setAccessible(true);
				this.classIdFields.put(entityClass, entityField);
				break;
			}
		}
	}

	@Override
	protected PreparedStatement getPreparedStatement(Object entity) throws SQLException {
		final Class<?> entityClass = entity.getClass();

		if (!getPreparedStatements().containsKey(entityClass)) {
			final Field idField = classIdFields.get(entityClass);

			if (idField != null) {
				final XmlAttribute xmlAttribute = idField.getAnnotation(XmlAttribute.class);
				final String sqlField = transformIdentifier(xmlAttribute.name());

				final String sql = "DELETE FROM " + transformIdentifier(entityClass.getSimpleName()) + " WHERE " + sqlField + " = ?";
				LOGGER.info("{} Delete SQL: {}", entityClass.getSimpleName(), sql);
				getPreparedStatements().put(entityClass, getConnection().prepareStatement(sql));
			} else {
				throw new RuntimeException(entityClass.getName() + " is not supported by EntityDeleter");
			}
		}

		return getPreparedStatements().get(entityClass);
	}

	@Override
	protected void setParameters(PreparedStatement preparedStatement, Object entity) throws
	                                                                                 IllegalAccessException,
	                                                                                 SQLException {
		final Field idField = classIdFields.get(entity.getClass());
		final Object value = idField.get(entity);
		preparedStatement.setObject(1, value);
	}
}
