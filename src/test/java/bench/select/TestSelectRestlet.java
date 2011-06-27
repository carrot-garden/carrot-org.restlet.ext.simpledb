package bench.select;

import java.util.List;

import org.restlet.data.MediaType;
import org.restlet.data.Reference;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bench.put.Account;

import com.carrotgarden.utils.json.JSON;

public class TestSelectRestlet {

	static final Logger log = LoggerFactory.getLogger(TestSelectRestlet.class);

	public static void main(String[] args) throws Exception {

		String expression = "where first_name like 'And%'";

		String uri = "http://localhost:8181/volumes/accounts/select/"
				+ Reference.encode(expression);

		log.debug("uri : {}", uri);

		Reference reference = new Reference(uri);

		ClientResource client = new ClientResource(reference);

		log.debug("client : {}", client);

		//

		Representation entity = client.get(MediaType.APPLICATION_JSON);

		String json = entity.getText();

		log.debug("json : {}", json);

		@SuppressWarnings("unchecked")
		List<Account> list = JSON.fromText(json, List.class);

		log.debug("list : {}", list);

	}

}
