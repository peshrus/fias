package com.peshchuk.fias.xml.saver;

import com.github.junrar.exception.RarException;
import org.junit.Before;
import org.junit.Test;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import static org.junit.Assert.assertNotNull;

/**
 * @author Ruslan Peshchuk (peshrus@gmail.com)
 */
public class FiasXmlSaverTest {
	private FiasXmlSaver saver;

	@Before
	public void setUp() throws URISyntaxException, JAXBException {
		final URL fiasRarUrl = FiasXmlSaverTest.class.getClassLoader().getResource("fias_xml.rar");
		assertNotNull(fiasRarUrl);
		saver = new FiasXmlSaver(new File(new URI(fiasRarUrl.toString()).getPath()));
	}

	@Test
	public void testSave_Ok() throws IOException, RarException, JAXBException {
		saver.save();
	}
}