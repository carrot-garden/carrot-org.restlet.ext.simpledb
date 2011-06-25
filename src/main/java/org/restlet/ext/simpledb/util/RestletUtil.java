package org.restlet.ext.simpledb.util;

import org.restlet.Message;
import org.restlet.resource.UniformResource;

public class RestletUtil {

	public static String componentURI(String path) {
		String uri = "riap://component" + path;
		return uri;
	}

	public static String getReqAttr(UniformResource resource, String name) {
		String attr = (String) resource.getRequestAttributes().get(name);
		return attr;
	}

	public static String getRspAttr(UniformResource resource, String name) {
		String attr = (String) resource.getResponseAttributes().get(name);
		return attr;
	}

	public static String getAttr(Message message, String name) {
		String attr = (String) message.getAttributes().get(name);
		return attr;
	}

}
