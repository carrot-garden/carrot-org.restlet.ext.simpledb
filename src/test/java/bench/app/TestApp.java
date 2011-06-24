package bench.app;

import java.util.List;

import org.restlet.ext.simpledb.props.AWSCredentialsLoader;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.simpledb.AmazonSimpleDB;
import com.amazonaws.services.simpledb.AmazonSimpleDBClient;
import com.amazonaws.services.simpledb.model.ListDomainsResult;

public class TestApp {

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

	}

}
