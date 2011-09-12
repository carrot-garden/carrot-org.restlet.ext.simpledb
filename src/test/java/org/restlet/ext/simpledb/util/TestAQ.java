package org.restlet.ext.simpledb.util;

import static org.junit.Assert.*;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestAQ {

	static final Logger log = LoggerFactory.getLogger(TestAQ.class);

	@Test
		public void testEnd() {
	
			log.debug(//
			AQ.where().attr("user_name").eq("andrei")//
					.and().attr("vendor").eq("barchart")//
					.and().attr("id").ne("123").end());
	
			assertTrue(true);
	
		}
}
