package org.restlet.ext.simpledb.app;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.ext.simpledb.name.Name;
import org.restlet.ext.simpledb.props.SDBProperties;
import org.restlet.ext.simpledb.props.SDBPropertiesLoader;
import org.restlet.ext.simpledb.resource.RootResource;
import org.restlet.routing.Router;

import com.amazonaws.services.simpledb.AmazonSimpleDB;
import com.amazonaws.services.simpledb.AmazonSimpleDBClient;

public class DomainsApp extends Application {

	private AmazonSimpleDB sdb;

	public DomainsApp() {

		try {
			SDBProperties creds = SDBPropertiesLoader.load();
			sdb = new AmazonSimpleDBClient(creds);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}

	}

	@Override
	public Restlet createInboundRoot() {

		Router router = new Router(getContext());

		router.attach(Name.NONE, RootResource.class);

		return router;

	}

}
