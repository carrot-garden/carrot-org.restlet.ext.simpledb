package org.restlet.ext.simpledb.api;

import java.util.List;

public interface Volumes {

	Volume load(String volumeId);

	List<Volume> listVolumes();

	Volume create(String volumeId, int domainCount);

	void delete(String volumeId);

}
