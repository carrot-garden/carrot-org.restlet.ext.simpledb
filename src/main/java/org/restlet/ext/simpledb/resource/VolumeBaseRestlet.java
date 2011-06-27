package org.restlet.ext.simpledb.resource;

import org.restlet.Context;
import org.restlet.Restlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.services.simpledb.AmazonSimpleDB;

public abstract class VolumeBaseRestlet extends Restlet {

	protected final Logger log = LoggerFactory.getLogger(getClass());

	protected final AmazonSimpleDB client;
	protected final VolumeMap volumes;

	public VolumeBaseRestlet(final Context context, final AmazonSimpleDB client,
			final VolumeMap volumes) {

		super(context);

		this.client = client;
		this.volumes = volumes;

	}

}