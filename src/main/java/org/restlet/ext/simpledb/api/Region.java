package org.restlet.ext.simpledb.api;

public interface Region<E> {

	String getRegionId();

	int getRegionShardCount();

	//

	E load(String entryId);

	void save(String entryId, E entry);

	void remove(String entryId);

}
