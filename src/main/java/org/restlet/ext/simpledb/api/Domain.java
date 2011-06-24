package org.restlet.ext.simpledb.api;

public interface Domain {

	String load(String entryId);

	void save(String entryId, String entryJson);

	void remove(String entryId);

}
