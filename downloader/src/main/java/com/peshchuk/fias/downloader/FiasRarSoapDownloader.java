package com.peshchuk.fias.downloader;

import com.peshchuk.fias.service.DownloadFileInfo;
import com.peshchuk.fias.service.DownloadService;
import com.peshchuk.fias.service.DownloadServiceSoap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;

/**
 * @author Ruslan Peshchuk (peshrus@gmail.com)
 */
public class FiasRarSoapDownloader {
	private static final Logger LOGGER = LoggerFactory.getLogger(FiasRarSoapDownloader.class);

	private final File fileToSave;
	private final boolean downloadDelta;

	public FiasRarSoapDownloader(File fileToSave) {
		this(fileToSave, true);
	}

	public FiasRarSoapDownloader(File fileToSave, boolean downloadDelta) {
		this.fileToSave = fileToSave;
		this.downloadDelta = downloadDelta;
	}

	public void download() throws IOException {
		MDC.put("context", "fias_xml.rar");
		final String fiasRarUrlStr = getFiasRarUrl();

		LOGGER.info("Start Downloading: {} (to: {})", fiasRarUrlStr, fileToSave);
		doDownload(fiasRarUrlStr);
		LOGGER.info("Finish Downloading: {}", fileToSave);
	}

	private String getFiasRarUrl() {
		final DownloadService downloadService = new DownloadService();
		final DownloadServiceSoap downloadServiceSoap12 = downloadService.getDownloadServiceSoap12();
		final DownloadFileInfo lastDownloadFileInfo = downloadServiceSoap12.getLastDownloadFileInfo();
		final String result = downloadDelta ?
				lastDownloadFileInfo.getFiasDeltaXmlUrl() :
				lastDownloadFileInfo.getFiasCompleteXmlUrl();

		return result;
	}

	private void doDownload(String fiasRarUrlStr) throws IOException {
		final URL fiasRarUrl = new URL(fiasRarUrlStr);
		try (final InputStream fiasRarStream = fiasRarUrl.openStream();
		     final ReadableByteChannel fiasRarStreamChannel = Channels.newChannel(fiasRarStream)) {
			try (final FileOutputStream fileStream = new FileOutputStream(fileToSave);
			     final FileChannel fileChannel = fileStream.getChannel()) {
				long read = 0;
				long portionRead;
				while ((portionRead = fileChannel.transferFrom(fiasRarStreamChannel, read, Short.MAX_VALUE)) > 0) {
					read += portionRead;
					LOGGER.trace("Downloaded: {} bytes", read);
				}
			}
		}
	}
}