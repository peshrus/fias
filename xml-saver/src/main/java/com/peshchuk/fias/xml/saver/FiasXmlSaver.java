package com.peshchuk.fias.xml.saver;

import com.github.junrar.Archive;
import com.github.junrar.exception.RarException;
import com.github.junrar.rarfile.FileHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Ruslan Peshchuk (peshrus@gmail.com)
 */
public class FiasXmlSaver {
	private static final Logger LOGGER = LoggerFactory.getLogger(FiasXmlSaver.class);

	private final File fiasRarFile;
	private final Unmarshaller jaxbUnmarshaller;

	public FiasXmlSaver(File fiasRarFile) throws JAXBException {
		this.fiasRarFile = fiasRarFile;

		final JAXBContext jaxbContext = JAXBContext.newInstance("com.peshchuk.fias.jaxb");
		jaxbUnmarshaller = jaxbContext.createUnmarshaller();
	}

	public void save() throws IOException, RarException, JAXBException {
		LOGGER.info("Start Saving: {}", fiasRarFile);
		try {
			final Archive archive = new Archive(fiasRarFile);

			if (!archive.isEncrypted()) {
				FileHeader fileHeader;
				while ((fileHeader = archive.nextFileHeader()) != null) {
					if (!fileHeader.isEncrypted()) {
						LOGGER.info("Reading: {}", fileHeader.getFileNameString());
						if (fileHeader.isFileHeader()) {
							try (final InputStream inputStream = archive.getInputStream(fileHeader)) {
								Object unmarshal = jaxbUnmarshaller.unmarshal(inputStream);
								System.out.println(unmarshal);
							}
						} else {
							LOGGER.error("File cannot been read: {}", fileHeader.getFileNameString());
						}
					} else {
						LOGGER.error("File is encrypted cannot read: {}", fileHeader.getFileNameString());
					}
				}
			} else {
				LOGGER.error("Archive is encrypted cannot read: {}", fiasRarFile);
			}
		} finally {
			LOGGER.info("Finish Saving: {}", fiasRarFile);
		}
	}
}