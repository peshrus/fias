package com.peshchuk.fias.xml.saver;

import org.junit.Before;
import org.junit.Test;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertNotNull;

/**
 * @author Ruslan Peshchuk (peshrus@gmail.com)
 */
public class FiasXmlSaverTest {
	private FiasXmlSaver saver;

	@Before
	public void setUp() throws URISyntaxException, JAXBException {
		final URL fiasRarUrl = FiasXmlSaverTest.class.getClassLoader().getResource("fias_delta_xml.rar");
		assertNotNull(fiasRarUrl);
		saver = new FiasXmlSaver(new File(new URI(fiasRarUrl.toString()).getPath()), 1000, new BatchSaver() {
			@Override
			public void save(Collection<?> batch) {
				System.out.println(Arrays.toString(batch.toArray()));
			}
		});
	}

	@Test
	public void testSave_Ok() throws Exception {
		saver.save();
	}
}