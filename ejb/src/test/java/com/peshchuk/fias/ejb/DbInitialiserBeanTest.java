package com.peshchuk.fias.ejb;

import java.util.Properties;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.NamingException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * @author Ruslan Peshchuk(peshrus@gmail.com)
 */
@Ignore
public class DbInitialiserBeanTest {
	private static EJBContainer container;

	@BeforeClass
	public static void setUpOnce() {
		// Create some configuration properties for initialization
		final Properties p = new Properties();

		// A default test datasource
		p.setProperty("fiasDs", "new://Resource?type=DataSource");
		p.setProperty("fiasDs.JdbcDriver", "org.h2.Driver");
		p.setProperty("fiasDs.JdbcUrl",
		              "jdbc:h2:~/DbInitialiserBeanTest;MODE=PostgreSQL;MVCC=TRUE;LOCK_TIMEOUT=20000;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=TRUE");

		// Create the container with our properties
		container = EJBContainer.createEJBContainer(p);
	}

	@AfterClass
	public static void tearDownOnce() {
		if (container != null) {
			container.close();
		}
	}

	@Test
	public void test() throws NamingException {
		final DbInitialiserBean dbInitialiserBean =
				DbInitialiserBean.class.cast(container.getContext().lookup("java:global/ejb/DbInitialiserBean"));
		assertNotNull(dbInitialiserBean);
	}
}