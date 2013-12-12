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
import java.util.*;

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
	private final int batchSize;
	private final BatchSaver batchSaver;
	private final Unmarshaller jaxbUnmarshaller;

	public FiasXmlSaver(File fiasRarFile, int batchSize, BatchSaver batchSaver) throws JAXBException {
		this.fiasRarFile = fiasRarFile;
		this.batchSize = batchSize;
		this.batchSaver = batchSaver;

		final JAXBContext jaxbContext = JAXBContext.newInstance(JAXB_CLASSES_PACKAGE);
		jaxbUnmarshaller = jaxbContext.createUnmarshaller();
	}

	public void save() throws Exception {
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
				final Collection<Object> batch = new ArrayList<>(batchSize);
				FileHeader fileHeader;

				while ((fileHeader = archive.nextFileHeader()) != null) {
					if (!fileHeader.isEncrypted()) {
						if (fileHeader.isFileHeader()) {
							LOGGER.info("Reading: {}", fileHeader.getFileNameString());

							try (final InputStream inputStream = archive.getInputStream(fileHeader)) {
								final XMLStreamReader xmlStreamReader = xmlInputFactory.createXMLStreamReader(inputStream);
								try {
									saveFile(xmlRootElements, entityClasses, xmlStreamReader, batch);
								} finally {
									xmlStreamReader.close();
								}
							} catch (Exception e) {
								LOGGER.error(e.toString(), e);
							}
						} else {
							LOGGER.error("File cannot be read: {}", fileHeader.getFileNameString());
						}
					} else {
						LOGGER.error("File is encrypted: {}", fileHeader.getFileNameString());
					}
				}

				if (batch.size() > 0) {
					saveBatch(batch);
				}
			} else {
				LOGGER.error("Archive is encrypted: {}", fiasRarFile);
			}
		} finally {
			LOGGER.info("Finish Saving: {}", fiasRarFile);
		}
	}

	static Set<Class<?>> getJaxbClasses() {
		final Reflections reflections = new Reflections(JAXB_CLASSES_PACKAGE);
		final Set<Class<?>> result = reflections.getTypesAnnotatedWith(XmlRootElement.class);

		return result;
	}

	private void saveFile(Set<String> xmlRootElements,
	                      Map<String, Class<?>> entityClasses,
	                      XMLStreamReader xmlStreamReader,
	                      Collection<Object> batch) throws Exception {
		do {
			xmlStreamReader.nextTag();
		} while (xmlRootElements.contains(xmlStreamReader.getLocalName()));

		do {
			final Class<?> entityClass = entityClasses.get(xmlStreamReader.getLocalName());
			final Object entity = saveEntity(xmlStreamReader, entityClass);
			batch.add(entity);

			if (batch.size() == batchSize) {
				saveBatch(batch);
			}
		} while (xmlStreamReader.isStartElement() && xmlStreamReader.hasNext());
	}

	private <T> T saveEntity(XMLStreamReader xmlStreamReader, Class<T> entityClass) throws JAXBException {
		final JAXBElement<T> jaxbElement = jaxbUnmarshaller.unmarshal(xmlStreamReader, entityClass);
		final T result = jaxbElement.getValue();

		return result;
	}

	private void saveBatch(Collection<Object> batch) throws Exception {
		batchSaver.save(batch);
		batch.clear();
	}
}