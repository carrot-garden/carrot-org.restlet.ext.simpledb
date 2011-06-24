package org.restlet.ext.simpledb.props;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class PropsLoader {

	public static boolean isInvalid(String value) {
		return value == null || value.length() == 0;
	}

	public static String fromEnvironmentVariable(String key) throws Exception {
		String value = System.getenv(key);
		if (isInvalid(value)) {
			throw new Exception(//
					"environment varialbe missing : " + key);
		}
		return value;
	}

	public static String fromSystemProperty(String key) throws Exception {
		String value = System.getProperty(key);
		if (isInvalid(value)) {
			throw new Exception(//
					"system property missing : " + key);
		}
		return value;
	}

	public static String fromPropertiesCollection(Properties props, String key)
			throws Exception {
		String value = props.getProperty(key);
		if (isInvalid(value)) {
			throw new Exception(//
					"property missing : " + key);
		}
		return value;
	}

	public static Properties fromEnvironmentVariables(String... names)
			throws Exception {
		Properties properties = new Properties();
		for (String key : names) {
			String value = fromEnvironmentVariable(key);
			properties.put(key, value);
		}
		return properties;
	}

	public static Properties fromSystemProperties(String... names)
			throws Exception {
		Properties properties = new Properties();
		for (String key : names) {
			String value = fromSystemProperty(key);
			properties.put(key, value);
		}
		return properties;
	}

	public static Properties fromClassPath(String classPath, String... names)
			throws Exception {
		Properties source = fromClassPath(classPath);
		Properties target = new Properties();
		for (String key : names) {
			String value = fromPropertiesCollection(source, key);
			target.put(key, value);
		}
		return target;
	}

	public static Properties fromClassPath(String classPath) throws Exception {
		Properties properties = new Properties();
		ClassLoader loader = PropsLoader.class.getClassLoader();
		InputStream input = loader.getResourceAsStream(classPath);
		properties.load(input);
		input.close();
		return properties;
	}

	public static Properties fromFilePath(String filePath, String... names)
			throws Exception {
		Properties source = fromFilePath(filePath);
		Properties target = new Properties();
		for (String key : names) {
			String value = fromPropertiesCollection(source, key);
			target.put(key, value);
		}
		return target;
	}

	public static Properties fromFilePath(String filePath) throws Exception {
		Properties properties = new Properties();
		InputStream input = new FileInputStream(filePath);
		properties.load(input);
		input.close();
		return properties;
	}

}
