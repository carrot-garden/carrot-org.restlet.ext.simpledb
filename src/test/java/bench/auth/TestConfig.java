package bench.auth;

import java.util.List;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.simpledb.AmazonSimpleDB;
import com.amazonaws.services.simpledb.AmazonSimpleDBClient;
import com.amazonaws.services.simpledb.model.GetAttributesRequest;
import com.amazonaws.services.simpledb.model.GetAttributesResult;
import com.amazonaws.services.simpledb.model.ListDomainsResult;

public class TestConfig {

	private static void log(String text) {
		System.err.println(text);
	}

	public static void main(String[] args) throws Exception {

		AWSCredentials creds = AWSCredentialsLoader.load();

		AmazonSimpleDB sdb = new AmazonSimpleDBClient(creds);

		ListDomainsResult listResult = sdb.listDomains();

		List<String> names = listResult.getDomainNames();
		for (String name : names) {
			log("name : " + name);
		}

		String domain1 = "simple_config";
		// sdb.createDomain(new CreateDomainRequest(domain1));

		String item1 = "restlet.properties";
		GetAttributesRequest request = new GetAttributesRequest(domain1, item1);
		GetAttributesResult result = sdb.getAttributes(request);

		log("result = " + result);

	}

}
