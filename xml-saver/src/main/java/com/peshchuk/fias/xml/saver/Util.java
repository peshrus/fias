package com.peshchuk.fias.xml.saver;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import org.reflections.Reflections;

/**
 * @author Ruslan Peshchuk(peshrus@gmail.com)
 */
public final class Util {
	/**
	 * See jaxb2-maven-plugin configuration in pom.xml
	 */
	static final String JAXB_CLASSES_PACKAGE = "com.peshchuk.fias.jaxb";

	private Util() {
		// Prevents an instance creation
	}

	public static Set<Class<?>> getJaxbClasses() {
		final Reflections reflections = new Reflections(JAXB_CLASSES_PACKAGE);
		final Set<Class<?>> result = reflections.getTypesAnnotatedWith(XmlRootElement.class);

		return result;
	}
}