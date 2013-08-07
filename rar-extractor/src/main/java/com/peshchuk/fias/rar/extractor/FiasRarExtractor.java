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

	public void extract() throws IOException {
		LOGGER.info("Start Extraction: {} (to: {})", fiasRarFile, fiasUnrarFolder);
		try {
			doExtract(fiasRarFile, fiasUnrarFolder);
		} finally {
			LOGGER.info("Finish Extraction: {}", fiasUnrarFolder);
		}
	}

	public static void doExtract(File rar, File destination) {
		Archive arch = null;
		try {
			arch = new Archive(rar);
		} catch (RarException | IOException e) {
			LOGGER.error(e.toString(), e);
		}
		if (arch != null) {
			if (arch.isEncrypted()) {
				LOGGER.warn("archive is encrypted cannot extreact");
				return;
			}
			FileHeader fh = null;
			while (true) {
				fh = arch.nextFileHeader();
				if (fh == null) {
					break;
				}
				if (fh.isEncrypted()) {
					LOGGER.warn("file is encrypted cannot extract: {}", fh.getFileNameString());
					continue;
				}
				LOGGER.info("extracting: {}", fh.getFileNameString());
				try {
					if (fh.isDirectory()) {
						createDirectory(fh, destination);
					} else {
						File f = createFile(fh, destination);
						OutputStream stream = new FileOutputStream(f);
						arch.extractFile(fh, stream);
						stream.close();
					}
				} catch (IOException e) {
					LOGGER.error("error extracting the file", e);
				} catch (RarException e) {
					LOGGER.error("error extraction the file", e);
				}
			}
		}
	}

	private static File createFile(FileHeader fh, File destination) {
		File f = null;
		String name = null;
		if (fh.isFileHeader() && fh.isUnicode()) {
			name = fh.getFileNameW();
		} else {
			name = fh.getFileNameString();
		}
		f = new File(destination, name);
		if (!f.exists()) {
			try {
				f = makeFile(destination, name);
			} catch (IOException e) {
				LOGGER.error("error creating the new file: {}", f.getName(), e);
			}
		}
		return f;
	}

	private static File makeFile(File destination, String name)
			throws IOException {
		String[] dirs = name.split("\\\\");
		if (dirs == null) {
			return null;
		}
		String path = "";
		int size = dirs.length;
		if (size == 1) {
			return new File(destination, name);
		} else if (size > 1) {
			for (int i = 0; i < dirs.length - 1; i++) {
				path = path + File.separator + dirs[i];
				new File(destination, path).mkdir();
			}
			path = path + File.separator + dirs[dirs.length - 1];
			File f = new File(destination, path);
			f.createNewFile();
			return f;
		} else {
			return null;
		}
	}

	private static void createDirectory(FileHeader fh, File destination) {
		File f = null;
		if (fh.isDirectory() && fh.isUnicode()) {
			f = new File(destination, fh.getFileNameW());
			if (!f.exists()) {
				makeDirectory(destination, fh.getFileNameW());
			}
		} else if (fh.isDirectory() && !fh.isUnicode()) {
			f = new File(destination, fh.getFileNameString());
			if (!f.exists()) {
				makeDirectory(destination, fh.getFileNameString());
			}
		}
	}

	private static void makeDirectory(File destination, String fileName) {
		String[] dirs = fileName.split("\\\\");
		if (dirs == null) {
			return;
		}
		String path = "";
		for (String dir : dirs) {
			path = path + File.separator + dir;
			new File(destination, path).mkdir();
		}

	}
}