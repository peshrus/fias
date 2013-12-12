package com.peshchuk.fias.xml.saver;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.junrar.Archive;
import com.github.junrar.rarfile.FileHeader;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

/**
 * @author Ruslan Peshchuk (peshrus@gmail.com)
 */
public class FiasXmlSaver {
	private static final Logger LOGGER = LoggerFactory.getLogger(FiasXmlSaver.class);

	private final File fiasRarFile;
	private final Set<Class<?>> jaxbClasses;
	private final int batchSize;
	private final BatchSaver batchSaver;
	private final Unmarshaller jaxbUnmarshaller;

	public FiasXmlSaver(File fiasRarFile, Set<Class<?>> jaxbClasses, int batchSize, BatchSaver batchSaver) throws
	                                                                                                       JAXBException {
		this.fiasRarFile = fiasRarFile;
		this.jaxbClasses = jaxbClasses;
		this.batchSize = batchSize;
		this.batchSaver = batchSaver;

		final JAXBContext jaxbContext = JAXBContext.newInstance(Util.JAXB_CLASSES_PACKAGE);
		jaxbUnmarshaller = jaxbContext.createUnmarshaller();
	}

	public void save() throws Exception {
		final Set<String> xmlRootElements = Sets.newHashSetWithExpectedSize(jaxbClasses.size());
		final Map<String, Class<?>> entityClasses = Maps.newHashMapWithExpectedSize(jaxbClasses.size());

		LOGGER.info("Analyze JAXB Classes");
		analyzeJaxbClasses(xmlRootElements, entityClasses);

		LOGGER.info("Start Saving: {}", fiasRarFile);
		try {
			final Archive archive = new Archive(fiasRarFile);

			if (!archive.isEncrypted()) {
				final XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
				final Collection<Object> batch = new ArrayList<>(batchSize);
				FileHeader fileHeader;

				while ((fileHeader = archive.nextFileHeader()) != null) {
					final String fileName = fileHeader.getFileNameString();

					if (!fileHeader.isEncrypted()) {
						if (fileHeader.isFileHeader()) {
							LOGGER.info("Start Reading: {}", fileName);

							try (final InputStream inputStream = archive.getInputStream(fileHeader)) {
								final XMLStreamReader xmlStreamReader = xmlInputFactory.createXMLStreamReader(inputStream);

								try {
									saveFile(xmlRootElements, entityClasses, xmlStreamReader, batch);
								} finally {
									xmlStreamReader.close();
								}
							} catch (Exception e) {
								LOGGER.error("Save Error: {}", fileName, e);
							} finally {
								LOGGER.info("Finish Reading: {}", fileName);
							}
						} else {
							LOGGER.error("Not File: {}", fileName);
						}
					} else {
						LOGGER.error("File is encrypted: {}", fileName);
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

	private void analyzeJaxbClasses(Set<String> xmlRootElements, Map<String, Class<?>> entityClasses) {
		for (Class<?> jaxbClass : jaxbClasses) {
			final String xmlRootElement = jaxbClass.getAnnotation(XmlRootElement.class).name();
			xmlRootElements.add(xmlRootElement);

			for (Class<?> entityClass : jaxbClass.getClasses()) {
				entityClasses.put(entityClass.getSimpleName(), entityClass);
			}
		}
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
			final Object entity = extractEntity(xmlStreamReader, entityClass);
			batch.add(entity);

			if (batch.size() == batchSize) {
				saveBatch(batch);
			}
		} while (xmlStreamReader.isStartElement() && xmlStreamReader.hasNext());
	}

	private <T> T extractEntity(XMLStreamReader xmlStreamReader, Class<T> entityClass) throws JAXBException {
		final JAXBElement<T> jaxbElement = jaxbUnmarshaller.unmarshal(xmlStreamReader, entityClass);
		final T result = jaxbElement.getValue();

		return result;
	}

	private void saveBatch(Collection<?> batch) throws Exception {
		batchSaver.save(batch);
		batch.clear();
	}
}