package org.restlet.ext.simpledb.api;

public interface Volume extends Domain {

	String getDomainPrefix();

	String getDomainSeparator();

	int getDomainCount();

	boolean isActive();

	String getUriId();

}
