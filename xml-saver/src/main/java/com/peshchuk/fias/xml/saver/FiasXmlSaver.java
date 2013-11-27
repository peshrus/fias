package com.peshchuk.fias.xml.saver;

import com.github.junrar.Archive;
import com.github.junrar.exception.RarException;
import com.github.junrar.rarfile.FileHeader;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Ruslan Peshchuk (peshrus@gmail.com)
 */
public class FiasXmlSaver {
	private static final Logger LOGGER = LoggerFactory.getLogger(FiasXmlSaver.class);

	/**
	 * See jaxb2-maven-plugin configuration in pom.xml
	 */
	private static final String JAXB_CLASSES_PACKAGE = "com.peshchuk.fias.jaxb";

	private final File fiasRarFile;
	private final Unmarshaller jaxbUnmarshaller;

	public FiasXmlSaver(File fiasRarFile) throws JAXBException {
		this.fiasRarFile = fiasRarFile;

		final JAXBContext jaxbContext = JAXBContext.newInstance(JAXB_CLASSES_PACKAGE);
		jaxbUnmarshaller = jaxbContext.createUnmarshaller();
	}

	public void save() throws IOException, RarException, JAXBException, ClassNotFoundException {
		final Set<Class<?>> jaxbClasses = getJaxbClasses();
		final Set<String> xmlRootElements = new HashSet<>();
		final Map<String, Class<?>> entityClasses = new HashMap<>();

		for (Class<?> jaxbClass : jaxbClasses) {
			final String xmlRootElement = jaxbClass.getAnnotation(XmlRootElement.class).name();
			xmlRootElements.add(xmlRootElement);

			for (Class<?> entityClass : jaxbClass.getClasses()) {
				entityClasses.put(entityClass.getSimpleName(), entityClass);
			}
		}

		LOGGER.info("Start Saving: {}", fiasRarFile);
		try {
			final Archive archive = new Archive(fiasRarFile);

			if (!archive.isEncrypted()) {
				final XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
				FileHeader fileHeader;
				while ((fileHeader = archive.nextFileHeader()) != null) {
					if (!fileHeader.isEncrypted()) {
						if (fileHeader.isFileHeader()) {
							LOGGER.info("Reading: {}", fileHeader.getFileNameString());

							try (final InputStream inputStream = archive.getInputStream(fileHeader)) {
								final XMLStreamReader xmlStreamReader = xmlInputFactory.createXMLStreamReader(inputStream);
								try {
									saveFile(xmlRootElements, entityClasses, xmlStreamReader);
								} finally {
									xmlStreamReader.close();
								}
							} catch (XMLStreamException e) {
								LOGGER.error(e.toString(), e);
							}
						} else {
							LOGGER.error("File cannot be read: {}", fileHeader.getFileNameString());
						}
					} else {
						LOGGER.error("File is encrypted: {}", fileHeader.getFileNameString());
					}
				}
			} else {
				LOGGER.error("Archive is encrypted: {}", fiasRarFile);
			}
		} finally {
			LOGGER.info("Finish Saving: {}", fiasRarFile);
		}
	}

	private Set<Class<?>> getJaxbClasses() {
		final Reflections reflections = new Reflections(JAXB_CLASSES_PACKAGE);
		final Set<Class<?>> result = reflections.getTypesAnnotatedWith(XmlRootElement.class);

		return result;
	}

	private void saveFile(Set<String> xmlRootElements, Map<String, Class<?>> entityClasses, XMLStreamReader xmlStreamReader) throws XMLStreamException, JAXBException {
		do {
			xmlStreamReader.nextTag();
		} while (xmlRootElements.contains(xmlStreamReader.getLocalName()));

		do {
			final Class<?> entityClass = entityClasses.get(xmlStreamReader.getLocalName());
			saveEntity(xmlStreamReader, entityClass);
		} while (xmlStreamReader.isStartElement() && xmlStreamReader.hasNext());
	}

	private <T> void saveEntity(XMLStreamReader xmlStreamReader, Class<T> entityClass) throws JAXBException {
		final JAXBElement<T> jaxbElement = jaxbUnmarshaller.unmarshal(xmlStreamReader, entityClass);
		System.out.println(jaxbElement.getValue());
	}
}