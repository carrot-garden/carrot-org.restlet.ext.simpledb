package org.restlet.ext.simpledb.resource;

import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;

public class EntrySR extends ServerResource {

	@Get("json")
	public Representation load() {

		return null;

	}

	@Put("json")
	public void save(Representation entity) throws Exception {

		String entry = entity.getText();

	}

}
