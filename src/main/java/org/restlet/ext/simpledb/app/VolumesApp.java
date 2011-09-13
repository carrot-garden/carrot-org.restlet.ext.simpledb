package org.restlet.ext.simpledb.app;

import java.util.List;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.ext.simpledb.api.Volume;
import org.restlet.ext.simpledb.bean.VolumeBean;
import org.restlet.ext.simpledb.name.Name;
import org.restlet.ext.simpledb.props.SDBProperties;
import org.restlet.ext.simpledb.props.SDBPropertiesLoader;
import org.restlet.ext.simpledb.props.SDBVolumesProperties;
import org.restlet.ext.simpledb.resource.VolumeEntryRestlet;
import org.restlet.ext.simpledb.resource.VolumeMap;
import org.restlet.ext.simpledb.resource.VolumeSelectRestlet;
import org.restlet.ext.simpledb.util.SimpleUtil;
import org.restlet.ext.simpledb.util.VolumeUtil;
import org.restlet.routing.Router;
import org.restlet.routing.TemplateRoute;
import org.restlet.routing.Variable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.services.simpledb.AmazonSimpleDB;
import com.amazonaws.services.simpledb.AmazonSimpleDBClient;

public class VolumesApp extends Application {

	private static final Logger log = LoggerFactory.getLogger(VolumesApp.class);

	private final VolumeMap volumes = new VolumeMap();

	private final SDBProperties sdbProps;
	private final AmazonSimpleDB client;

	public VolumesApp() {

		try {

			sdbProps = SDBPropertiesLoader.load();

			client = new AmazonSimpleDBClient(sdbProps);

			configureVolumes();

		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}

	}

	private void configureVolumes() throws Exception {

		String domain = sdbProps.getSDBConfigDomain();
		String propsItem = SDBVolumesProperties.ITEM_VOLUMES_PROPS;

		SDBVolumesProperties props = SimpleUtil.getObject(//
				client, domain, propsItem, SDBVolumesProperties.class);

		log.debug("props : {}", props);

		String prefix = props.getSearchPrefix();

		List<VolumeBean> volumeList = SimpleUtil.findItems(client, domain,
				prefix, VolumeBean.class);

		for (Volume volume : volumeList) {
			if (volume.isActive()) {
				log.debug("volume : {}", volume);
				VolumeUtil.makeDomains(client, volume);
				volumes.put(volume.getUriId(), volume);
			}
		}

	}

	@Override
	public Restlet createInboundRoot() {

		final Router router = new Router(getContext());

		final TemplateRoute routeEntry = router.attach(Name.ENTRY, //
				new VolumeEntryRestlet(getContext(), client, volumes));

		final TemplateRoute routeSelect = router.attach(Name.SELECT, //
				new VolumeSelectRestlet(getContext(), client, volumes));

		final Variable var = new Variable();

		var.setDecodingOnParse(true);

		routeSelect.getTemplate().getVariables().put(Name.Id.SELECT, var);

		return router;

	}

}
