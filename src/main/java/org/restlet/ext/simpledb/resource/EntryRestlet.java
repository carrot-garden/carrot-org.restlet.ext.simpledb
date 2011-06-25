package org.restlet.ext.simpledb.resource;

import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;
import org.restlet.data.MediaType;
import org.restlet.data.Method;
import org.restlet.data.Status;
import org.restlet.ext.simpledb.api.Volume;
import org.restlet.ext.simpledb.name.Name;
import org.restlet.ext.simpledb.util.RestletUtil;
import org.restlet.ext.simpledb.util.SimpleUtil;
import org.restlet.ext.simpledb.util.VolumeUtil;
import org.restlet.representation.Representation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.services.simpledb.AmazonSimpleDB;

public class EntryRestlet extends Restlet {

	private static final Logger log = LoggerFactory
			.getLogger(EntryRestlet.class);

	private final AmazonSimpleDB sdbClient;
	private final VolumeMap volumeMap;

	public EntryRestlet(AmazonSimpleDB sdbClient, VolumeMap volumeMap) {
		this.sdbClient = sdbClient;
		this.volumeMap = volumeMap;
	}

	@Override
	public void handle(Request request, Response response) {

		super.handle(request, response);

		Method method = request.getMethod();

		if (method == Method.GET) {

			try {

				log.debug("get");

				String volumeId = RestletUtil.getAttr(request, Name.Id.VOLUME);
				if (volumeId == null) {
					response.setStatus(Status.CLIENT_ERROR_BAD_REQUEST,
							"missing volume attribute");
					return;
				}

				String entryId = RestletUtil.getAttr(request, Name.Id.ENTRY);
				if (entryId == null) {
					response.setStatus(Status.CLIENT_ERROR_BAD_REQUEST,
							"missing entry attribute");
					return;
				}

				Volume volume = volumeMap.get(volumeId);
				if (volume == null) {
					response.setStatus(Status.SERVER_ERROR_SERVICE_UNAVAILABLE,
							"volume not found / not active");
					return;
				}

				String prefix = VolumeUtil.getPrefix(volume, entryId);

				String domain = VolumeUtil.getDomain(volume, prefix);

				String json = SimpleUtil.getJson(sdbClient, domain, entryId);
				if (json == null) {
					response.setStatus(Status.CLIENT_ERROR_NOT_FOUND,
							"entry not found");
					return;
				}

				MediaType mediaType = MediaType.APPLICATION_JSON;

				response.setEntity(json, mediaType);

				response.setStatus(Status.SUCCESS_OK);

			} catch (Exception e) {

				log.error("", e);

				response.setStatus(Status.SERVER_ERROR_INTERNAL);
			}

			return;

		}

		if (method == Method.PUT) {

			log.debug("put");

			Representation entity = request.getEntity();

			MediaType mediaType = entity.getMediaType();

			if (mediaType != MediaType.APPLICATION_JSON) {
				response.setStatus(Status.CLIENT_ERROR_UNSUPPORTED_MEDIA_TYPE);
				return;
			}

			return;

		}

		response.setStatus(Status.CLIENT_ERROR_METHOD_NOT_ALLOWED);

	}

}
