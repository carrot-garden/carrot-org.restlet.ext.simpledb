package org.restlet.ext.simpledb.resource;

import org.restlet.Context;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.MediaType;
import org.restlet.data.Method;
import org.restlet.data.Status;
import org.restlet.ext.simpledb.api.Volume;
import org.restlet.ext.simpledb.name.Name;
import org.restlet.ext.simpledb.util.RestletUtil;
import org.restlet.ext.simpledb.util.SimpleUtil;
import org.restlet.representation.Representation;

import com.amazonaws.services.simpledb.AmazonSimpleDB;

public class VolumeEntryRestlet extends VolumeBaseRestlet {

	public VolumeEntryRestlet(Context rstContex, AmazonSimpleDB sdbClient,
			VolumeMap volumeMap) {
		super(rstContex, sdbClient, volumeMap);

	}

	@Override
	public void handle(final Request request, final Response response) {

		super.handle(request, response);

		final String volumeId = RestletUtil.getAttr(request, Name.Id.VOLUME);
		if (volumeId == null) {
			response.setStatus(Status.CLIENT_ERROR_BAD_REQUEST,
					"missing volume attribute");
			return;
		}

		final String entry = RestletUtil.getAttr(request, Name.Id.ENTRY);
		if (entry == null) {
			response.setStatus(Status.CLIENT_ERROR_BAD_REQUEST,
					"missing entry attribute");
			return;
		}

		final Volume volume = volumes.get(volumeId);
		if (volume == null) {
			response.setStatus(Status.SERVER_ERROR_SERVICE_UNAVAILABLE,
					"volume not found / not active");
			return;
		}

		final String domain = volume.getDomainName(entry);

		final Method method = request.getMethod();

		try {

			if (method == Method.GET) {
				doGet(request, response, domain, entry);
				return;
			}

			if (method == Method.PUT) {
				doPut(request, response, domain, entry);
				return;
			}

			if (method == Method.DELETE) {
				doDelete(request, response, domain, entry);
				return;
			}

			response.setStatus(Status.CLIENT_ERROR_METHOD_NOT_ALLOWED);

		} catch (Exception e) {

			log.error("", e);

			response.setStatus(Status.SERVER_ERROR_INTERNAL);
		}

	}

	protected void doGet(final Request request, final Response response,
			final String domain, final String entry) throws Exception {

		final String json = SimpleUtil.getJson(client, domain, entry);

		if (json == null) {
			response.setStatus(Status.CLIENT_ERROR_NOT_FOUND, "entry not found");
			return;
		}

		response.setEntity(json, MediaType.APPLICATION_JSON);

		response.setStatus(Status.SUCCESS_OK);

	}

	protected void doPut(final Request request, final Response response,
			final String domain, final String entry) throws Exception {

		final Representation entity = request.getEntity();

		if (!MediaType.APPLICATION_JSON.isCompatible(entity.getMediaType())) {
			response.setStatus(Status.CLIENT_ERROR_UNSUPPORTED_MEDIA_TYPE,
					"expected type is json");
			return;
		}

		final String json = request.getEntityAsText();

		SimpleUtil.putJson(json, client, domain, entry);

		response.setStatus(Status.SUCCESS_OK);

	}

	protected void doDelete(final Request request, final Response response,
			final String domain, final String entry) throws Exception {

		SimpleUtil.putProps(null, client, domain, entry);

		response.setStatus(Status.SUCCESS_OK);

	}

}
