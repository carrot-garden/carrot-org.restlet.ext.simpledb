package bench.select;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.restlet.Client;
import org.restlet.Context;
import org.restlet.data.MediaType;
import org.restlet.data.Protocol;
import org.restlet.data.Reference;
import org.restlet.ext.simpledb.name.Name;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;
import org.restlet.routing.Template;
import org.restlet.routing.Variable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bench.put.Account;

import com.carrotgarden.utils.json.JSON;

public class TestSelectRestlet {

	static final Logger log = LoggerFactory.getLogger(TestSelectRestlet.class);

	public static void main(String[] args) throws Exception {

		//

		String volume = "accounts";
		String expression = "where first_name like 'And%'";

		Template template = new Template(Name.SELECT);
		Variable var = new Variable();
		var.setEncodingOnFormat(true);
		template.getVariables().put(Name.Id.SELECT, var);

		Map<String, String> values = new HashMap<String, String>();
		values.put(Name.Id.VOLUME, volume);
		values.put(Name.Id.SELECT, expression);

		String uri = "http://localhost:8181" + template.format(values);

		log.debug("uri : {}", uri);

		Reference reference = new Reference(uri);

		Client client = new Client(new Context(), Protocol.HTTP);
		client.getContext().getParameters().add("tracing", "true");

		// TemplateRoute route = new TemplateRoute(client);

		ClientResource resource = new ClientResource(reference);
		resource.setNext(client);

		// resource.getRequestAttributes().put("select", expression);
		// resource.getReference().addQueryParameter("select-3", expression);

		log.debug("client : {}", client);

		//

		Representation entity = resource.get(MediaType.APPLICATION_JSON);

		String json = entity.getText();

		log.debug("json : {}", json);

		@SuppressWarnings("unchecked")
		List<Account> list = JSON.fromText(json, List.class);

		log.debug("list : {}", list);

	}

}
