package org.restlet.ext.simpledb.util;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;

import com.amazonaws.services.simpledb.AmazonSimpleDB;
import com.amazonaws.services.simpledb.model.Attribute;
import com.amazonaws.services.simpledb.model.CreateDomainRequest;
import com.amazonaws.services.simpledb.model.DeleteAttributesRequest;
import com.amazonaws.services.simpledb.model.GetAttributesRequest;
import com.amazonaws.services.simpledb.model.GetAttributesResult;
import com.amazonaws.services.simpledb.model.Item;
import com.amazonaws.services.simpledb.model.ListDomainsResult;
import com.amazonaws.services.simpledb.model.PutAttributesRequest;
import com.amazonaws.services.simpledb.model.ReplaceableAttribute;
import com.amazonaws.services.simpledb.model.SelectRequest;
import com.amazonaws.services.simpledb.model.SelectResult;
import com.amazonaws.services.simpledb.model.UpdateCondition;
import com.carrotgarden.utils.json.JSON;

public class SimpleUtil {

	public static List<Item> getItems(AmazonSimpleDB client, String domain,
			String prefix) throws Exception {

		String expression = "select * from " + name(domain)
				+ " where itemName() like " + value(prefix + "%")
				+ " limit 2500 ";

		SelectRequest request = new SelectRequest(expression)
				.withConsistentRead(true);

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

		Map<String, Object> props = getMap(client, domain, item);

		if (props.isEmpty()) {
			return null;
		}

		String json = JSON.intoText(props);

		return json;

	}

	public static Map<String, Object> getMap(AmazonSimpleDB client,
			String domain, String item) throws Exception {

		GetAttributesRequest request = new GetAttributesRequest(//
				domain, item).withConsistentRead(true);

		GetAttributesResult result = client.getAttributes(request);

		List<Attribute> attributes = result.getAttributes();

		Map<String, Object> props = getMap(attributes);

		return props;

	}

	public static Map<String, Object> getMap(Item item) throws Exception {

		List<Attribute> attributes = item.getAttributes();

		Map<String, Object> props = getMap(attributes);

		return props;

	}

	@SuppressWarnings("unchecked")
	public static Map<String, Object> getMap(List<Attribute> attributes)
			throws Exception {

		Map<String, Object> props = new HashMap<String, Object>();

		for (Attribute attrib : attributes) {

			String key = attrib.getName();
			String value = attrib.getValue();

			Object stored = props.get(key);

			if (stored == null) {
				props.put(key, value);
				continue;
			}

			if (stored instanceof String) {
				List<String> list = new LinkedList<String>();
				list.add((String) stored);
				list.add(value);
				props.put(key, list);
				continue;
			}

			if (stored instanceof List) {
				List<String> list = (List<String>) stored;
				list.add(value);
				continue;
			}

			throw new Exception("unexpected value type : " + stored);

		}

		return props;

	}

	public static <T> T getObject(AmazonSimpleDB client, String domain,
			String item, Class<T> klaz) throws Exception {

		Map<String, Object> props = getMap(client, domain, item);

		T value = getObject(props, klaz);

		return value;

	}

	public static <T> T getObject(Item item, Class<T> klaz) throws Exception {

		List<Attribute> attributes = item.getAttributes();

		Map<String, Object> props = getMap(attributes);

		T object = getObject(props, klaz);

		return object;

	}

	public static <T> T getObject(Map<String, Object> props, Class<T> klaz)
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

	public static void putMap(Map<String, Object> props, AmazonSimpleDB client,
			String domain, String item) throws Exception {

		if (props == null) {

			List<Attribute> attributes = null;
			UpdateCondition condition = null;

			DeleteAttributesRequest request = new DeleteAttributesRequest(
					domain, item, attributes, condition);

			client.deleteAttributes(request);

		} else {

			List<ReplaceableAttribute> attributes = new LinkedList<ReplaceableAttribute>();
			putMap(props, attributes);

			UpdateCondition condition = null;

			PutAttributesRequest request = new PutAttributesRequest(domain,
					item, attributes, condition);

			client.putAttributes(request);

		}

	}

	public static void putEntry(String key, String value,
			List<ReplaceableAttribute> attributes) {

		ReplaceableAttribute attrib = new ReplaceableAttribute();

		attrib.setName(key);
		attrib.setValue(value);
		attrib.setReplace(true); // XXX

		attributes.add(attrib);

	}

	@SuppressWarnings({ "unchecked" })
	public static void putMap(Map<String, Object> props,
			List<ReplaceableAttribute> attributes) throws Exception {

		for (Map.Entry<String, Object> entry : props.entrySet()) {

			String key = entry.getKey();
			Object stored = entry.getValue();

			if (stored instanceof String) {
				String value = (String) stored;
				putEntry(key, value, attributes);
				continue;
			}

			if (stored instanceof List) {
				List<String> list = (List<String>) stored;
				for (String value : list) {
					putEntry(key, value, attributes);
				}
				continue;
			}

			throw new Exception("unexpected value type : " + stored);

		}

	}

}
