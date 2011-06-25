package org.restlet.ext.simpledb.resource;

import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.ext.simpledb.api.Volume;
import org.restlet.ext.simpledb.name.Name;
import org.restlet.ext.simpledb.util.RestletUtil;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EntrySR extends ServerResource {

	private static final Logger log = LoggerFactory.getLogger(EntrySR.class);

	public EntrySR() {
		setAnnotated(false);
		setNegotiated(false);
	}

	protected Volume getVolume() {

		String volumeId = RestletUtil.getReqAttr(this, Name.Id.VOLUME);

		return null;

	}

	@Override
	protected void doInit() throws ResourceException {

	}

	@Override
	protected Representation get() throws ResourceException {
		try {

			String json = "{ a=1 }";

			log.debug("json : {}", json);

			Representation entity = new StringRepresentation(json);

			entity.setMediaType(MediaType.APPLICATION_JSON);

			return entity;

		} catch (Exception e) {
			throw new ResourceException(e);
		}
	}

	@Override
	protected Representation put(Representation entity)
			throws ResourceException {
		try {

			if (entity.getMediaType() != MediaType.APPLICATION_JSON) {
				setStatus(Status.CLIENT_ERROR_UNSUPPORTED_MEDIA_TYPE);
				return entity;
			}

			String json = entity.getText();
			log.debug("json : {}", json);

			return entity;

		} catch (Exception e) {
			throw new ResourceException(e);
		}
	}

}
