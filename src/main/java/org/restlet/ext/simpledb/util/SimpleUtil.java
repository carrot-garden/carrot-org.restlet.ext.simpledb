package org.restlet.ext.simpledb.util;

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
import com.carrotgarden.util.json.JSON;

public class SimpleUtil {

	public static String convertProps(Props props) throws Exception {

		if (props == null) {
			return null;
		}

		String json = JSON.intoText(props);

		return json;

	}

	public static <T> T convertProps(Props props, Class<T> klaz)
			throws Exception {

		if (props == null) {
			return null;
		}

		ObjectMapper mapper = JSON.getInstance();

		T value = mapper.convertValue(props, klaz);

		return value;

	}

	public static Props convertInstance(Object instance) throws Exception {

		if (instance == null) {
			return null;
		}

		ObjectMapper mapper = JSON.getInstance();

		Props props = mapper.convertValue(instance, Props.class);

		return props;

	}

	public static Props convertJson(String json) throws Exception {

		if (json == null) {
			return null;
		}

		Props props = JSON.fromText(json, Props.class);

		return props;

	}

	public static List<Item> selectItems(final AmazonSimpleDB client,
			final String domain, final String select) throws Exception {

		final String expression = "select * from " + name(domain) + " "
				+ select;

		final SelectRequest request = new SelectRequest(expression)
				.withConsistentRead(true);

		final SelectResult result = client.select(request);

		final List<Item> itemList = result.getItems();

		return itemList;

	}

	public static <T> List<T> findItems(final AmazonSimpleDB client,
			final String domain, final String prefix, final Class<T> klaz)
			throws Exception {

		final String select = //
		"where itemName() like " + value(prefix + "%") + " limit 2500";

		final List<Item> itemList = selectItems(client, domain, select);

		final List<T> objectList = new LinkedList<T>();

		for (final Item item : itemList) {
			T object = getObject(item, klaz);
			objectList.add(object);
		}

		return objectList;

	}

	public static String getJson(Item item) throws Exception {

		Props props = SimpleUtil.getProps(item);

		String json = convertProps(props);

		return json;

	}

	public static String getJson(AmazonSimpleDB client, String domain,
			String item) throws Exception {

		Props props = getProps(client, domain, item);

		String json = convertProps(props);

		return json;

	}

	public static <T> T getObject(AmazonSimpleDB client, String domain,
			String item, Class<T> klaz) throws Exception {

		Props props = getProps(client, domain, item);

		T value = convertProps(props, klaz);

		return value;

	}

	public static <T> T getObject(Item item, Class<T> klaz) throws Exception {

		List<Attribute> attributes = item.getAttributes();

		Props props = getProps(attributes);

		T object = convertProps(props, klaz);

		return object;

	}

	public static Props getProps(AmazonSimpleDB client, String domain,
			String item) throws Exception {

		GetAttributesRequest request = new GetAttributesRequest(//
				domain, item).withConsistentRead(true);

		GetAttributesResult result = client.getAttributes(request);

		List<Attribute> attributes = result.getAttributes();

		Props props = getProps(attributes);

		return props;

	}

	public static Props getProps(final Item item) throws Exception {

		final List<Attribute> attributes = item.getAttributes();

		final Props props = getProps(attributes);

		return props;

	}

	@SuppressWarnings("unchecked")
	public static Props getProps(final List<Attribute> attributes)
			throws Exception {

		if (attributes.isEmpty()) {
			return null;
		}

		final Props props = new Props();

		for (final Attribute attrib : attributes) {

			final String key = attrib.getName();
			final String value = attrib.getValue();

			final Object stored = props.get(key);

			/* first insert - save as string */
			if (stored == null) {
				props.put(key, value);
				continue;
			}

			/* second insert - convert to list */
			if (stored instanceof String) {
				List<String> list = new LinkedList<String>();
				list.add((String) stored);
				list.add(value);
				props.put(key, list);
				continue;
			}

			/* third insert - keep adding to list */
			if (stored instanceof List) {
				List<String> list = (List<String>) stored;
				list.add(value);
				continue;
			}

			throw new Exception("unexpected value type : " + stored);

		}

		return props;

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

	public static void putEntry(String key, String value,
			List<ReplaceableAttribute> attributes) {

		ReplaceableAttribute attrib = new ReplaceableAttribute();

		attrib.setName(key);
		attrib.setValue(value);

		attrib.setReplace(true); // XXX

		attributes.add(attrib);

	}

	public static void putJson(String json, AmazonSimpleDB client,
			String domain, String item) throws Exception {

		Map<String, Object> props = convertJson(json);

		putProps(props, client, domain, item);

	}

	public static void putObject(Object instance, AmazonSimpleDB client,
			String domain, String item) throws Exception {

		Map<String, Object> props = convertInstance(instance);

		putProps(props, client, domain, item);

	}

	public static void putProps(Map<String, Object> props,
			AmazonSimpleDB client, String domain, String item) throws Exception {

		if (props == null) {

			List<Attribute> attributes = null;
			UpdateCondition condition = null;

			DeleteAttributesRequest request = new DeleteAttributesRequest(//
					domain, item, attributes, condition);

			client.deleteAttributes(request);

		} else {

			List<ReplaceableAttribute> attributes = new LinkedList<ReplaceableAttribute>();

			putProps(props, attributes);

			UpdateCondition condition = null;

			PutAttributesRequest request = new PutAttributesRequest(//
					domain, item, attributes, condition);

			client.putAttributes(request);

		}

	}

	public static boolean isPrimitive(final Object value) {
		if (value instanceof String) {
			return true;
		}
		if (value instanceof Number) {
			return true;
		}
		if (value instanceof Boolean) {
			return true;
		}
		return false;
	}

	@SuppressWarnings({ "unchecked" })
	public static void putProps(final Map<String, Object> props,
			final List<ReplaceableAttribute> attributes) throws Exception {

		for (final Map.Entry<String, Object> entry : props.entrySet()) {

			final String key = entry.getKey();
			final Object stored = entry.getValue();

			if (isPrimitive(stored)) {
				final String value = stored.toString();
				putEntry(key, value, attributes);
				continue;
			}

			if (stored instanceof List) {
				final List<Object> list = (List<Object>) stored;
				for (final Object item : list) {
					if (isPrimitive(item)) {
						final String value = item.toString();
						putEntry(key, value, attributes);
					} else {
						throw new Exception("unknown item type : " + item);
					}
				}
				continue;
			}

			throw new Exception("unknown stored type : " + stored);

		}

	}

	// http://docs.amazonwebservices.com/AmazonSimpleDB/2009-04-15/DeveloperGuide/index.html?QuotingRulesSelect.html
	public static String value(String value) {
		return "'" + value + "'";
	}

}
