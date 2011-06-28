package bench.select;

import org.restlet.Client;
import org.restlet.Component;
import org.restlet.data.Protocol;
import org.restlet.data.Reference;
import org.restlet.ext.simpledb.name.Name;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestComp extends Component {

	static final Logger log = LoggerFactory.getLogger(TestComp.class);

	public static void main(String[] args) throws Exception {

		String uri = "http://localhost:8181" + Name.SELECT;
		log.debug("uri : {}", uri);

		Reference ref = new Reference(uri);
		log.debug("ref : {}", ref);

		TestComp comp = new TestComp();

		Client client = comp.getContext().getClientDispatcher();
		log.debug("client : {}", client);

		ClientResource resource = new ClientResource(uri);

		resource.getRequestAttributes().put("volume", "accounts");
		resource.getRequestAttributes().put("select", "where a=b");

		resource.setNext(client);

		Representation entity = resource.get();

	}

	TestComp() {

		Client clientHTTP = getClients().add(Protocol.HTTP);
		clientHTTP.getContext().getParameters().set("tracing", "true");

		//

		// TestApp app = new TestApp();
		// getDefaultHost().attachDefault(app);

	}

}
