package bench.auth;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.restlet.ext.simpledb.props.AWSCredentialsLoader;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.simpledb.AmazonSimpleDB;
import com.amazonaws.services.simpledb.AmazonSimpleDBClient;
import com.amazonaws.services.simpledb.model.Attribute;
import com.amazonaws.services.simpledb.model.CreateDomainRequest;
import com.amazonaws.services.simpledb.model.Item;
import com.amazonaws.services.simpledb.model.ListDomainsResult;
import com.amazonaws.services.simpledb.model.PutAttributesRequest;
import com.amazonaws.services.simpledb.model.ReplaceableAttribute;
import com.amazonaws.services.simpledb.model.SelectRequest;

public class TestAccess {

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

		String domain1 = "platform-accounts";
		sdb.createDomain(new CreateDomainRequest(domain1));
		String domain2 = "platform-accounts-tester";
		sdb.createDomain(new CreateDomainRequest(domain2));

		PutAttributesRequest putRequest = new PutAttributesRequest();
		putRequest.setDomainName(domain2);
		putRequest.setItemName("account-one");
		Collection<ReplaceableAttribute> attributes = new LinkedList<ReplaceableAttribute>();
		// attributes.add(new ReplaceableAttribute("user-name",
		// "user-name-value-one", true));
		// attributes.add(new ReplaceableAttribute("user-name",
		// "user-name-value-two", true));
		attributes.add(new ReplaceableAttribute("user-name",
				"user-name-value-three", true));
		putRequest.setAttributes(attributes);
		sdb.putAttributes(putRequest);

		String selectExpression = "select * from `" + domain2 + "`";
		SelectRequest selectRequest = new SelectRequest(selectExpression);
		for (Item item : sdb.select(selectRequest).getItems()) {
			log("  Item");
			log("    Name: " + item.getName());
			for (Attribute attribute : item.getAttributes()) {
				log("      Attribute");
				log("        Name:  " + attribute.getName());
				log("        Value: " + attribute.getValue());
			}
		}

	}

}
