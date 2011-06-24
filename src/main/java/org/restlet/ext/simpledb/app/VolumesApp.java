package org.restlet.ext.simpledb.app;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.ext.simpledb.name.Name;
import org.restlet.ext.simpledb.props.SDBProperties;
import org.restlet.ext.simpledb.props.SDBPropertiesLoader;
import org.restlet.ext.simpledb.props.SDBRestletProperties;
import org.restlet.ext.simpledb.resource.RootResource;
import org.restlet.ext.simpledb.resource.VolumeServerResource;
import org.restlet.ext.simpledb.util.SimpleUtil;
import org.restlet.routing.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.services.simpledb.AmazonSimpleDB;
import com.amazonaws.services.simpledb.AmazonSimpleDBClient;

public class VolumesApp extends Application {

	private static final Logger log = LoggerFactory.getLogger(VolumesApp.class);

	private final SDBProperties sdbProps;

	private final AmazonSimpleDB sdbClient;

	public VolumesApp() {

		try {

			sdbProps = SDBPropertiesLoader.load();

			sdbClient = new AmazonSimpleDBClient(sdbProps);

			configureVolumes();

		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}

	}

	private void configureVolumes() throws Exception {

		String configDomain = sdbProps.getSDBConfigDomain();
		String restletProperties = SDBRestletProperties.ITEM_RESTLET_PROPS;

		SDBRestletProperties props = SimpleUtil.fromDomainItem(sdbClient,
				configDomain, restletProperties, SDBRestletProperties.class);

		log.debug("props : {}", props);

	}

	@Override
	public Restlet createInboundRoot() {

		Router router = new Router(getContext());

		router.attach(Name.NONE, RootResource.class);

		router.attach(Name.VOLUME, VolumeServerResource.class);

		return router;

	}

}
