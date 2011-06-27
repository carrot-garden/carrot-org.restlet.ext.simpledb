package org.restlet.ext.simpledb.api;

public interface Volume extends Domain {

	String getNameSeparator();

	String getDomainFormat();

	int getDomainCount();

	String getDomainName(int index);

	String getDomainName(String entry);

	boolean isActive();

	String getUriId();

}
