package com.peshchuk.fias.downloader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.peshchuk.fias.downloader.struct.FiasInfo;
import com.peshchuk.fias.service.DownloadFileInfo;
import com.peshchuk.fias.service.DownloadService;
import com.peshchuk.fias.service.DownloadServiceSoap;

/** @author Ruslan Peshchuk (peshrus@gmail.com) */
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

		LOGGER.info("Start Downloading: {} -> {} (version: {}; description: {})",
		            fiasInfo.getFiasRarUrl(),
		            fileToSave,
		            fiasInfo.getVersion(),
		            fiasInfo.getVersionDescription());
		try {
			doDownload(fiasInfo.getFiasRarUrl());
		} finally {
			LOGGER.info("Finish Downloading: {}", fileToSave);
		}

		saveDownloadHistory(fiasInfo.getVersion(), fiasInfo.getVersionDescription());
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
		     final ReadableByteChannel fiasRarStreamChannel = Channels.newChannel(fiasRarStream);
		     final FileOutputStream fileStream = new FileOutputStream(fileToSave);
		     final FileChannel fileChannel = fileStream.getChannel()) {
			long position = 0;
			long transferred;

			while ((transferred = fileChannel.transferFrom(fiasRarStreamChannel, position, Short.MAX_VALUE)) > 0) {
				position += transferred;
				LOGGER.trace("Downloaded: {} bytes", position);
			}
		}
	}

	private void saveDownloadHistory(int version, String versionDescription) {
		LOGGER.info("Download History Saving: version - {}; description - {}", version, versionDescription);
		// TODO implement saving to DB
	}
}