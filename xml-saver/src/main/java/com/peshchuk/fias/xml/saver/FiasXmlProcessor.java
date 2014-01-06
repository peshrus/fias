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
public class FiasXmlProcessor {
	private static final Logger LOGGER = LoggerFactory.getLogger(FiasXmlProcessor.class);

	private enum Mode {
		SAVE, DELETE
	}

	private final File fiasRarFile;
	private final Set<Class<?>> jaxbClasses;
	private final int batchSize;
	private final BatchProcessor batchProcessor;
	private final Unmarshaller jaxbUnmarshaller;

	public FiasXmlProcessor(File fiasRarFile,
	                        Set<Class<?>> jaxbClasses,
	                        int batchSize,
	                        BatchProcessor batchProcessor) throws JAXBException {
		this.fiasRarFile = fiasRarFile;
		this.jaxbClasses = jaxbClasses;
		this.batchSize = batchSize;
		this.batchProcessor = batchProcessor;

		final JAXBContext jaxbContext = JAXBContext.newInstance(Util.JAXB_CLASSES_PACKAGE);
		jaxbUnmarshaller = jaxbContext.createUnmarshaller();
	}

	public void process(ProcessingAssistant processingAssistant) throws Exception {
		final Set<String> xmlRootElements = Sets.newHashSetWithExpectedSize(jaxbClasses.size());
		final Map<String, Class<?>> entityClasses = Maps.newHashMapWithExpectedSize(jaxbClasses.size());

		LOGGER.info("Analyze JAXB Classes");
		analyzeJaxbClasses(xmlRootElements, entityClasses);

		process(Mode.SAVE, xmlRootElements, entityClasses);
		processingAssistant.createConstraints();
		process(Mode.DELETE, xmlRootElements, entityClasses);
		processingAssistant.finish();
	}

	private void process(Mode mode, Set<String> xmlRootElements, Map<String, Class<?>> entityClasses) throws Exception {
		LOGGER.info("Start {}: {}", mode, fiasRarFile);
		try {
			final Archive archive = new Archive(fiasRarFile);

			if (!archive.isEncrypted()) {
				final XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
				Collection<Object> batch = new ArrayList<>(batchSize);
				FileHeader fileHeader;

				while ((fileHeader = archive.nextFileHeader()) != null) {
					final String fileName = fileHeader.getFileNameString();

					if (!fileHeader.isEncrypted()) {
						if (fileHeader.isFileHeader()) {
							if ((!fileName.startsWith("AS_DEL_") && mode == Mode.SAVE) ||
							    (fileName.startsWith("AS_DEL_") && mode == Mode.DELETE)) {
								LOGGER.info("Start Reading: {}", fileName);

								try (final InputStream inputStream = archive.getInputStream(fileHeader)) {
									final XMLStreamReader xmlStreamReader = xmlInputFactory.createXMLStreamReader(inputStream);

									try {
										batch = processFile(mode, xmlRootElements, entityClasses, xmlStreamReader, batch);
									} finally {
										xmlStreamReader.close();
									}
								} catch (Exception e) {
									LOGGER.error("{} Error: {}", mode, fileName, e);
								} finally {
									LOGGER.info("Finish Reading: {}", fileName);
								}
							}
						} else {
							LOGGER.error("Not File: {}", fileName);
						}
					} else {
						LOGGER.error("File is encrypted: {}", fileName);
					}
				}

				if (batch.size() > 0) {
					processBatch(mode, batch);
				}
			} else {
				LOGGER.error("Archive is encrypted: {}", fiasRarFile);
			}
		} finally {
			LOGGER.info("Finish {}: {}", mode, fiasRarFile);
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

	private Collection<Object> processFile(Mode mode,
	                                       Set<String> xmlRootElements,
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
				batch = processBatch(mode, batch);
			}
		} while (xmlStreamReader.isStartElement() && xmlStreamReader.hasNext());

		return batch;
	}

	private <T> T extractEntity(XMLStreamReader xmlStreamReader, Class<T> entityClass) throws JAXBException {
		final JAXBElement<T> jaxbElement = jaxbUnmarshaller.unmarshal(xmlStreamReader, entityClass);
		final T result = jaxbElement.getValue();

		return result;
	}

	private Collection<Object> processBatch(Mode mode, Collection<Object> batch) throws Exception {
		switch (mode) {
			case SAVE:
				batchProcessor.save(batch);
				break;
			case DELETE:
				batchProcessor.delete(batch);
				break;
		}

		return new ArrayList<>(batchSize);
	}
}