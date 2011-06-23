package org.restlet.ext.simpledb.api;

import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Put;

public interface RegionsResource<E> {

	@Get("json")
	public Region<E> load();

	@Put("json")
	public void save(Region<E> region);

	@Delete("json")
	public void remove(Region<E> region);

}
