package org.restlet.ext.simpledb.resource;

import org.restlet.Context;
import org.restlet.resource.Finder;

public class VolumeFinder extends Finder {

	private final VolumeMap volumeMap;

	public VolumeFinder(Context context, VolumeMap map) {

		super(context);

		setTargetClass(VolumeSR.class);

		volumeMap = map;

	}

}
