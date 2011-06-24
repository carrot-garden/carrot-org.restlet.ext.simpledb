package org.restlet.ext.simpledb.api;

import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Put;

public interface VolumesResource<E> {

	@Get("json")
	public Volume<E> load();

	@Put("json")
	public void save(Volume<E> region);

	@Delete("json")
	public void remove(Volume<E> region);

}
