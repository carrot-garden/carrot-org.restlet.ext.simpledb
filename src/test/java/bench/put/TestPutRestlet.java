package bench.put;

import java.util.LinkedList;
import java.util.List;

import org.restlet.data.MediaType;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.ClientResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.carrotgarden.util.json.JSON;

public class TestPutRestlet {

	static final Logger log = LoggerFactory.getLogger(TestPutRestlet.class);

	public static void main(String[] args) throws Exception {

		String uri = "http://localhost:8181/volumes/accounts/entries/andrei";

		ClientResource client = new ClientResource(uri);

		log.debug("client : {}", client);

		Account account = new Account();
		account.setFirstName("Andrei");
		account.setLastName("Pozolotin");
		List<String> emailList = new LinkedList<String>();
		emailList.add("andrei@aaa.com");
		emailList.add("andrei@bbb.com");
		emailList.add("andrei@ccc.com");
		emailList.add("andrei@ddd.com");
		emailList.add("andrei@eee.com");
		emailList.add("andrei@eee.com");
		emailList.add("andrei@eee.com");
		emailList.add("andrei@eee.com");
		emailList.add("andrei@fff.com");
		emailList.add("andrei@111.com");
		emailList.add("andrei@222.com");
		account.setEmailList(emailList);

		String json = JSON.intoText(account);
		log.debug("account : {}", account);

		Representation entity = new StringRepresentation(json);
		entity.setMediaType(MediaType.APPLICATION_JSON);

		client.put(entity);

		//

		entity = client.get(MediaType.APPLICATION_JSON);

		json = entity.getText();

		account = JSON.fromText(json, Account.class);
		log.debug("account : {}", account);

	}

}
