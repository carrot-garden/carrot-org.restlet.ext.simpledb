package org.restlet.ext.simpledb.api;

public interface Counter {

	long get() throws Exception;

	long getNext() throws Exception;

}
