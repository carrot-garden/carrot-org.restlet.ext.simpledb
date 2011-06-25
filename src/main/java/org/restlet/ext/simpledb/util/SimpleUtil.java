package org.restlet.ext.simpledb.util;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;

import com.amazonaws.services.simpledb.AmazonSimpleDB;
import com.amazonaws.services.simpledb.model.Attribute;
import com.amazonaws.services.simpledb.model.CreateDomainRequest;
import com.amazonaws.services.simpledb.model.GetAttributesRequest;
import com.amazonaws.services.simpledb.model.GetAttributesResult;
import com.amazonaws.services.simpledb.model.Item;
import com.amazonaws.services.simpledb.model.ListDomainsResult;
import com.amazonaws.services.simpledb.model.SelectRequest;
import com.amazonaws.services.simpledb.model.SelectResult;
import com.carrotgarden.utils.json.JSON;

public class SimpleUtil {

	public static List<Item> getItems(AmazonSimpleDB client, String domain,
			String prefix) throws Exception {

		String expression = "select * from " + name(domain)
				+ " where itemName() like " + value(prefix + "%")
				+ " limit 2500 ";

		SelectRequest request = new SelectRequest(expression);

		SelectResult result = client.select(request);

		List<Item> itemList = result.getItems();

		return itemList;

	}

	public static <T> List<T> getItems(AmazonSimpleDB client, String domain,
			String prefix, Class<T> klaz) throws Exception {

		List<Item> itemList = getItems(client, domain, prefix);

		List<T> objectList = new LinkedList<T>();

		for (Item item : itemList) {
			T object = getObject(item, klaz);
			objectList.add(object);
		}

		return objectList;

	}

	public static String getJson(AmazonSimpleDB client, String domain,
			String item) throws Exception {

		Map<String, String> props = getMap(client, domain, item);

		if (props.isEmpty()) {
			return null;
		}

		String json = JSON.intoText(props);

		return json;

	}

	public static Map<String, String> getMap(AmazonSimpleDB client,
			String domain, String item) throws Exception {

		GetAttributesRequest request = new GetAttributesRequest(//
				domain, item);

		GetAttributesResult result = client.getAttributes(request);

		List<Attribute> attributes = result.getAttributes();

		Map<String, String> props = getMap(attributes);

		return props;

	}

	public static Map<String, String> getMap(Item item) throws Exception {

		List<Attribute> attributes = item.getAttributes();

		Map<String, String> props = getMap(attributes);

		return props;

	}

	public static Map<String, String> getMap(List<Attribute> attributes)
			throws Exception {

		Map<String, String> props = new HashMap<String, String>();

		for (Attribute attrib : attributes) {
			String key = attrib.getName();
			String value = attrib.getValue();
			props.put(key, value);

		}

		return props;

	}

	public static <T> T getObject(AmazonSimpleDB client, String domain,
			String item, Class<T> klaz) throws Exception {

		Map<String, String> props = getMap(client, domain, item);

		T value = getObject(props, klaz);

		return value;

	}

	public static <T> T getObject(Item item, Class<T> klaz) throws Exception {

		List<Attribute> attributes = item.getAttributes();

		Map<String, String> props = getMap(attributes);

		T object = getObject(props, klaz);

		return object;

	}

	public static <T> T getObject(Map<String, String> props, Class<T> klaz)
			throws Exception {

		ObjectMapper mapper = JSON.getInstance();

		T value = mapper.convertValue(props, klaz);

		return value;

	}

	public static void makeDomain(AmazonSimpleDB client, String domain)
			throws Exception {

		ListDomainsResult result = client.listDomains();

		List<String> nameList = result.getDomainNames();

		if (nameList.contains(domain)) {
			return;
		}

		CreateDomainRequest request = new CreateDomainRequest(domain);

		client.createDomain(request);

	}

	// http://docs.amazonwebservices.com/AmazonSimpleDB/2009-04-15/DeveloperGuide/index.html?QuotingRulesSelect.html
	public static String name(String name) {
		return "`" + name + "`";
	}

	// http://docs.amazonwebservices.com/AmazonSimpleDB/2009-04-15/DeveloperGuide/index.html?QuotingRulesSelect.html
	public static String value(String value) {
		return "'" + value + "'";
	}

}
