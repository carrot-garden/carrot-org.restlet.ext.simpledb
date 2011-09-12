package bench.put;

import java.util.LinkedList;
import java.util.List;

import org.restlet.ext.simpledb.props.SDBPropertiesLoader;
import org.restlet.ext.simpledb.util.SimpleUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.simpledb.AmazonSimpleDB;
import com.amazonaws.services.simpledb.AmazonSimpleDBClient;
import com.carrotgarden.util.json.JSON;

public class TestPutSimple {

	static final Logger log = LoggerFactory.getLogger(TestPutSimple.class);

	public static void main(String[] args) throws Exception {

		AWSCredentials creds = SDBPropertiesLoader.load();

		AmazonSimpleDB client = new AmazonSimpleDBClient(creds);

		log.debug("client : {}", client);

		Account account = new Account();
		account.setFirstName("Andrei");
		account.setLastName("Pozolotin");
		List<String> emailList = new LinkedList<String>();
		emailList.add("andrei@aaa.com");
		emailList.add("andrei@bbb.com");
		emailList.add("andrei@ccc.com");
		emailList.add("andrei@ddd.com");
		account.setEmailList(emailList);

		String domain = "platform_accounts";
		String item = "account-tester";

		String json = JSON.intoText(account);
		log.debug("account : {}", json);

		SimpleUtil.putJson(json, client, domain, item);

		//

		List<Account> accountList = SimpleUtil.findItems(client, domain, item,
				Account.class);

		log.debug("accountList : {}", accountList);

	}

}
