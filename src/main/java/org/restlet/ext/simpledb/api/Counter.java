package org.restlet.ext.simpledb.api;

public interface Counter {

	String PROP_VALUE = "counter_value";

	long getThis() throws Exception;

	long getNext() throws Exception;

}
