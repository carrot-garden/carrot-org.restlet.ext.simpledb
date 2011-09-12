package org.restlet.ext.simpledb.resource;

import java.util.List;

import org.restlet.Context;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.MediaType;
import org.restlet.data.Method;
import org.restlet.data.Status;
import org.restlet.ext.simpledb.api.Volume;
import org.restlet.ext.simpledb.name.Name;
import org.restlet.ext.simpledb.util.RestletUtil;
import org.restlet.ext.simpledb.util.VolumeUtil;

import com.amazonaws.services.simpledb.AmazonSimpleDB;
import com.carrotgarden.util.json.JSON;

public class VolumeSelectRestlet extends VolumeBaseRestlet {

	public VolumeSelectRestlet(Context rstContex, AmazonSimpleDB sdbClient,
			VolumeMap volumeMap) {
		super(rstContex, sdbClient, volumeMap);

	}

	@Override
	public void handle(Request request, Response response) {

		super.handle(request, response);

		final String volumeId = RestletUtil.getAttr(request, Name.Id.VOLUME);
		if (volumeId == null) {
			response.setStatus(Status.CLIENT_ERROR_BAD_REQUEST,
					"missing volume attribute");
			return;
		}

		final Volume volume = volumes.get(volumeId);
		if (volume == null) {
			response.setStatus(Status.SERVER_ERROR_SERVICE_UNAVAILABLE,
					"volume not found / not active");
			return;
		}

		final String select = RestletUtil.getAttr(request, Name.Id.SELECT);
		if (select == null) {
			response.setStatus(Status.CLIENT_ERROR_BAD_REQUEST,
					"missing select attribute");
			return;
		}

		final Method method = request.getMethod();

		log.debug("### attributes : {}", request.getAttributes());

		try {

			if (method == Method.GET) {
				doGet(request, response, volume, select);
				return;
			}

			response.setStatus(Status.CLIENT_ERROR_METHOD_NOT_ALLOWED);

		} catch (Exception e) {

			log.error("", e);

			response.setStatus(Status.SERVER_ERROR_INTERNAL);
		}

	}

	protected void doGet(Request request, Response response, Volume volume,
			String select) throws Exception {

		List<String> result = VolumeUtil.selectItems(client, volume, select);

		String json = JSON.intoText(result);

		response.setEntity(json, MediaType.APPLICATION_JSON);

		response.setStatus(Status.SUCCESS_OK);

	}

}
