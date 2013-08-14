package com.peshchuk.fias.downloader;

import com.peshchuk.fias.downloader.struct.FiasInfo;
import com.peshchuk.fias.service.DownloadFileInfo;
import com.peshchuk.fias.service.DownloadService;
import com.peshchuk.fias.service.DownloadServiceSoap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
		LOGGER.info("Getting FIAS information");
		final FiasInfo fiasInfo = getFiasInfo();

		LOGGER.info("Start Downloading: {} (to: {})", fiasInfo, fileToSave);
		try {
			doDownload(fiasInfo.getFiasRarUrl());
		} finally {
			LOGGER.info("Finish Downloading: {}", fileToSave);
		}

		LOGGER.info("Saving information about downloaded file");
		logDownloadHistory(fiasInfo.getVersion(), fiasInfo.getVersionDescription());
	}

	private FiasInfo getFiasInfo() {
		final DownloadService downloadService = new DownloadService();
		final DownloadServiceSoap downloadServiceSoap12 = downloadService.getDownloadServiceSoap12();
		final DownloadFileInfo lastDownloadFileInfo = downloadServiceSoap12.getLastDownloadFileInfo();
		final String fiasRarUrl = downloadDelta ?
				lastDownloadFileInfo.getFiasDeltaXmlUrl() :
				lastDownloadFileInfo.getFiasCompleteXmlUrl();
		final FiasInfo result = new FiasInfo(lastDownloadFileInfo.getVersionId(),
		                                     lastDownloadFileInfo.getTextVersion(),
		                                     fiasRarUrl);

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

	private void logDownloadHistory(int version, String versionDescription) {
		// TODO implement
	}
}