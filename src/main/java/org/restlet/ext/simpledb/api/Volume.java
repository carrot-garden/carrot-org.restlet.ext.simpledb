package org.restlet.ext.simpledb.api;

public interface Volume extends Domain {

	String PROP_FORMAT = "domain_format";
	String PROP_COUNT = "domain_count";
	String PROP_SEPARATOR = "name_separator";
	String PROP_ACTIVE = "is_active";
	String PROP_URI_ID = "uri_id";

	//

	String getNameSeparator();

	String getDomainFormat();

	int getDomainCount();

	String getDomainName(int index);

	String getDomainName(String entry);

	boolean isActive();

	String getUriId();

}
