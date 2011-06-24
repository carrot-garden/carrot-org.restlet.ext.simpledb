package org.restlet.ext.simpledb.api;

public interface Volume extends Domain {

	String getDomainPrefix();

	int getDomainCount();

	boolean isActive();

	String getUriId();

}
