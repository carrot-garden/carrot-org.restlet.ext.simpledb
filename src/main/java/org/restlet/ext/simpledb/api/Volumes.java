package org.restlet.ext.simpledb.api;

import java.util.List;

public interface Volumes {

	Volume<?> load(String volumeId);

	List<String> listVolumeIds();

	Volume<?> create(String volumeId, int shardCount);

	void delete(String volumeId);

}
