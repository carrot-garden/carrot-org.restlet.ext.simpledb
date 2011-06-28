package org.restlet.ext.simpledb.app;

import org.restlet.Client;
import org.restlet.Component;
import org.restlet.Server;
import org.restlet.data.Protocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestVolumesApp extends Component {

	static final Logger log = LoggerFactory.getLogger(TestVolumesApp.class);

	//

	static final int PORT = 8181;
	static final int PORT_SSL = 8182;

	//

	public static void main(String[] args) throws Exception {

		log.info("init");

		System.setProperty("java.net.preferIPv4Stack", "true");

		new TestVolumesApp().start();

		log.info("done");

	}

	public TestVolumesApp() throws Exception {

		Client clientFILE = getClients().add(Protocol.FILE);
		clientFILE.getContext().getParameters().set("tracing", "true");

		Client clientHTTP = getClients().add(Protocol.HTTP);
		clientHTTP.getContext().getParameters().set("tracing", "true");

		Client clientHTTPS = getClients().add(Protocol.HTTPS);
		clientHTTPS.getContext().getParameters().set("tracing", "true");

		//

		Server serverHTTP = getServers().add(Protocol.HTTP, PORT);
		serverHTTP.getContext().getParameters().set("tracing", "true");

		// Server serverHTTPS = getServers().add(Protocol.HTTPS, PORT_SSL);
		// serverHTTPS.getContext().getParameters().set("tracing", "true");

		//

		VolumesApp app = new VolumesApp();

		getDefaultHost().attachDefault(app);

		//

	}

}
