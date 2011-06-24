package org.restlet.ext.simpledb.api;

public interface Volume<E> {

	String getDomainPrefix();

	int getDomainCount();

	//

	E load(String entryId);

	void save(String entryId, E entry);

	void remove(String entryId);

}
