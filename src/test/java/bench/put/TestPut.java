package bench.put;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.restlet.ext.simpledb.props.SDBPropertiesLoader;
import org.restlet.ext.simpledb.util.SimpleUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.simpledb.AmazonSimpleDB;
import com.amazonaws.services.simpledb.AmazonSimpleDBClient;
import com.carrotgarden.utils.json.JSON;

public class TestPut {

	static final Logger log = LoggerFactory.getLogger(TestPut.class);

	public static void main(String[] args) throws Exception {

		AWSCredentials creds = SDBPropertiesLoader.load();

		AmazonSimpleDB sdb = new AmazonSimpleDBClient(creds);

		log.debug("sdb : {}", sdb);

		Account account = new Account();
		account.setFirstName("Andrei");
		account.setLastName("Pozolotin");
		List<String> emailList = new LinkedList<String>();
		emailList.add("andrei@aaa.com");
		emailList.add("andrei@bbb.com");
		emailList.add("andrei@ccc.com");
		account.setEmailList(emailList);

		ObjectMapper mapper = JSON.getInstance();

		@SuppressWarnings("unchecked")
		Map<String, Object> props = mapper.convertValue(account, Map.class);

		String domain = "platform_accounts";
		String item = "account-tester";
		SimpleUtil.putMap(props, sdb, domain, item);

		//

		List<Account> accountList = SimpleUtil.getItems(sdb, domain, item,
				Account.class);

		log.debug("accountList : {}", accountList);

	}

}
