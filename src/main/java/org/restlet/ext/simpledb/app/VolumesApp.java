package org.restlet.ext.simpledb.app;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.ext.simpledb.name.Name;
import org.restlet.ext.simpledb.props.AWSCredentialsLoader;
import org.restlet.ext.simpledb.props.SDBProperties;
import org.restlet.ext.simpledb.props.SDBPropertiesLoader;
import org.restlet.ext.simpledb.resource.RootResource;
import org.restlet.ext.simpledb.resource.VolumeServerResource;
import org.restlet.routing.Router;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.simpledb.AmazonSimpleDB;
import com.amazonaws.services.simpledb.AmazonSimpleDBClient;

public class VolumesApp extends Application {

	private final SDBProperties sdbProps;

	private final AmazonSimpleDB sdbClient;

	public VolumesApp() {

		try {

			sdbProps = SDBPropertiesLoader.load();

			AWSCredentials creds = AWSCredentialsLoader.load();
			sdbClient = new AmazonSimpleDBClient(creds);

		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}

	}

	@Override
	public Restlet createInboundRoot() {

		Router router = new Router(getContext());

		router.attach(Name.NONE, RootResource.class);

		router.attach(Name.VOLUME, VolumeServerResource.class);

		return router;

	}

}
