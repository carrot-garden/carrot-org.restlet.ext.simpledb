package org.restlet.ext.simpledb.props;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestSDBPropertiesLoader {

	@Test
	public void testLoad() throws Exception {

		SDBProperties props = SDBPropertiesLoader.load();

		String domain = props.getSDBConfigDomain();

		assertEquals(domain, "simple_config");

	}

}
