package bench.access;

import java.util.List;

import org.restlet.ext.simpledb.auth.AWSCredentialsLoader;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.simpledb.AmazonSimpleDB;
import com.amazonaws.services.simpledb.AmazonSimpleDBClient;
import com.amazonaws.services.simpledb.model.ListDomainsResult;

public class TestAccess {

	private static void log(String text) {
		System.err.println(text);
	}

	public static void main(String[] args) throws Exception {

		AWSCredentials creds = AWSCredentialsLoader.load();

		AmazonSimpleDB sdb = new AmazonSimpleDBClient(creds);

		ListDomainsResult result = sdb.listDomains();

		List<String> names = result.getDomainNames();
		for (String name : names) {
			log("name : " + name);
		}

	}

}
