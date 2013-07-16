package com.peshchuk.fias.downloader;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertTrue;

/**
 * @author Ruslan Peshchuk (peshrus@gmail.com)
 */
public class FiasRarSoapDownloaderTest {
	private File fiasRarFile;
	private FiasRarSoapDownloader downloader;

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