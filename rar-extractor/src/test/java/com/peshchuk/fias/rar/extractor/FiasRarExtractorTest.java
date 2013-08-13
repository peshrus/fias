package com.peshchuk.fias.rar.extractor;

import com.github.junrar.exception.RarException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Ruslan Peshchuk (peshrus@gmail.com)
 */
public class FiasRarExtractorTest {
	private File fiasUnrarFolder;
	private FiasRarExtractor extractor;

	@Before
	public void setUp() throws IOException, URISyntaxException {
		fiasUnrarFolder = new File(System.getProperty("java.io.tmpdir"), "fias");
		fiasUnrarFolder.deleteOnExit();

		extractor = new FiasRarExtractor(new File(new URI(FiasRarExtractorTest.class.getClassLoader().getResource("fias_delta_xml.rar").toString()).getPath()), fiasUnrarFolder);
	}

	@After
	public void tearDown() {
		final File[] fiasUnrarFolderFiles = fiasUnrarFolder.listFiles();

		if (fiasUnrarFolderFiles != null) {
			for (File file : fiasUnrarFolderFiles) {
				assertTrue(file.delete());
			}
		}
		assertTrue(fiasUnrarFolder.delete());
	}

	@Test
	public void testDownload_Ok() throws IOException, RarException {
		extractor.extract();
		assertEquals(13, fiasUnrarFolder.list() != null ? fiasUnrarFolder.list().length : 0);
	}
}