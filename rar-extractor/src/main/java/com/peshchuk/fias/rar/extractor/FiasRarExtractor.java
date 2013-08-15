package com.peshchuk.fias.rar.extractor;

import com.github.junrar.Archive;
import com.github.junrar.exception.RarException;
import com.github.junrar.rarfile.FileHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * This class demonstrates my refactoring skills.
 * Check out original code: <a href="https://github.com/edmund-wagner/junrar/blob/master/testutil/src/main/java/com/github/junrar/testutil/ExtractArchive.java">ExtractArchive.java</a>
 *
 * @author Ruslan Peshchuk (peshrus@gmail.com)
 */
public class FiasRarExtractor {
	private static final Logger LOGGER = LoggerFactory.getLogger(FiasRarExtractor.class);

	private final File fiasRarFile;
	private final File fiasUnrarFolder;

	public FiasRarExtractor(File fiasRarFile, File fiasUnrarFolder) {
		this.fiasRarFile = fiasRarFile;
		this.fiasUnrarFolder = fiasUnrarFolder;
	}

	public void extract() throws IOException, RarException {
		LOGGER.info("Start Extraction: {} (to: {})", fiasRarFile, fiasUnrarFolder);
		try {
			doExtract(fiasRarFile, fiasUnrarFolder);
		} finally {
			LOGGER.info("Finish Extraction: {}", fiasUnrarFolder);
		}
	}

	public static void doExtract(File rar, File destination) throws IOException, RarException {
		final Archive archive = new Archive(rar);

		if (!archive.isEncrypted()) {
			FileHeader fileHeader;
			while ((fileHeader = archive.nextFileHeader()) != null) {
				if (!fileHeader.isEncrypted()) {
					LOGGER.info("Extracting: {}", fileHeader.getFileNameString());
					final File file = create(destination, fileHeader);
					if (fileHeader.isFileHeader() && file != null) {
						try (final OutputStream stream = new FileOutputStream(file)) {
							archive.extractFile(fileHeader, stream);
						}
					} else if (fileHeader.isFileHeader()) {
						LOGGER.error("File has not been created: {}", fileHeader.getFileNameString());
					}
				} else {
					LOGGER.error("File is encrypted cannot extract: {}", fileHeader.getFileNameString());
				}
			}
		} else {
			LOGGER.error("Archive is encrypted cannot extract: {}", rar);
		}
	}

	private static File create(File destination, FileHeader fileHeader) throws IOException {
		final String name;
		if (fileHeader.isUnicode()) {
			name = fileHeader.getFileNameW();
		} else {
			name = fileHeader.getFileNameString();
		}

		File file = new File(destination, name);
		if (!file.exists()) {
			file = make(destination, name, fileHeader.isFileHeader());
		}

		return file;
	}

	private static File make(File destination, String name, boolean isFile) throws IOException {
		if (!destination.exists() && !destination.mkdirs()) {
			throw new RuntimeException("Path has not been created: " + destination);
		}

		final File file = new File(destination, name);
		if (isFile && !file.createNewFile()) {
			throw new RuntimeException("File has not been created: " + file);
		} else if (!isFile && !file.mkdir()) {
			throw new RuntimeException("Directory has not been created: " + file);
		}
		LOGGER.info("Created: {}", file);

		return file;
	}
}