package com.peshchuk.fias.downloader;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import static org.junit.Assert.assertTrue;

/**
 * @author Ruslan Peshchuk (peshrus@gmail.com)
 */
public class FiasRarSoapDownloaderTest {
	private File fiasRarFile;
	private FiasRarSoapDownloader downloader;

	@BeforeClass
	public static void setUpOnce() {
		final InputStream loggingCfgStreamStream = FiasRarSoapDownloaderTest.class.getResourceAsStream("/fias.logging.properties");
		try {
			LogManager.getLogManager().readConfiguration(loggingCfgStreamStream);
		} catch (IOException e) {
			Logger.getAnonymousLogger().severe("Could not load fias.logging.properties file");
			Logger.getAnonymousLogger().severe(e.getMessage());
		}
	}

	@Before
	public void setUp() throws IOException {
		fiasRarFile = File.createTempFile("fias_delta_xml_", ".rar");
		fiasRarFile.deleteOnExit();

		downloader = new FiasRarSoapDownloader(fiasRarFile);
	}

	@After
	public void tearDown() {
		assertTrue(fiasRarFile.delete());
	}

	@Test
	public void testDownload_Ok() throws IOException {
		downloader.download();
		assertTrue(fiasRarFile.exists());
	}
}