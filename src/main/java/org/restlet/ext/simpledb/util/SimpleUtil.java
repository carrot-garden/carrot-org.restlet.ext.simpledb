package org.restlet.ext.simpledb.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;

import com.amazonaws.services.simpledb.AmazonSimpleDB;
import com.amazonaws.services.simpledb.model.Attribute;
import com.amazonaws.services.simpledb.model.GetAttributesRequest;
import com.amazonaws.services.simpledb.model.GetAttributesResult;
import com.carrotgarden.utils.json.JSON;

public class SimpleUtil {

	public static Map<String, String> fromDomainItem(AmazonSimpleDB client,
			String domain, String item) throws Exception {

		Map<String, String> props = new HashMap<String, String>();

		GetAttributesRequest request = new GetAttributesRequest(//
				domain, item);

		GetAttributesResult result = client.getAttributes(request);

		List<Attribute> attributes = result.getAttributes();

		for (Attribute attrib : attributes) {
			String key = attrib.getName();
			String value = attrib.getValue();
			props.put(key, value);

		}

		return props;

	}

	public static <T> T fromDomainItem(AmazonSimpleDB client, String domain,
			String item, Class<T> klaz) throws Exception {

		ObjectMapper mapper = JSON.getInstance();

		Map<String, String> props = fromDomainItem(client, domain, item);

		T value = mapper.convertValue(props, klaz);

		return value;

	}

}
