package org.restlet.ext.simpledb.resource;

import org.restlet.Context;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.resource.Finder;
import org.restlet.resource.ServerResource;

public class VolumeFinder extends Finder {

	public static final String KEY = VolumeFinder.class.getName();

	private final VolumeMap volumeMap;

	public VolumeFinder(Context context, VolumeMap map,
			Class<ServerResource> klaz) {

		super(context, klaz);

		volumeMap = map;

	}

	@Override
	public void handle(Request request, Response response) {

		request.getAttributes().put(KEY, this);

		super.handle(request, response);

	}

}
