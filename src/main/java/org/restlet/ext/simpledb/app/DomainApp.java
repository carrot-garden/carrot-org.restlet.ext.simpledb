package org.restlet.ext.simpledb.app;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.ext.simpledb.auth.AWSCredentialsLoader;
import org.restlet.ext.simpledb.resource.RootResource;
import org.restlet.routing.Router;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.simpledb.AmazonSimpleDB;
import com.amazonaws.services.simpledb.AmazonSimpleDBClient;

public class DomainApp extends Application {

	private AmazonSimpleDB sdb;

	public DomainApp() {

		try {
			AWSCredentials creds = AWSCredentialsLoader.load();
			sdb = new AmazonSimpleDBClient(creds);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}

	}

	@Override
	public Restlet createInboundRoot() {

		Router router = new Router(getContext());

		router.attach("", RootResource.class);

		return router;

	}

}
