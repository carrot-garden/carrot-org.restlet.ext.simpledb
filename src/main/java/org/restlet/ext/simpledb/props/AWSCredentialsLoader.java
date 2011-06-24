package org.restlet.ext.simpledb.props;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import com.amazonaws.auth.AWSCredentials;

public class AWSCredentialsLoader implements AWSCredentials {

	/** file that stores aws id & secret */
	public static final String FILE_AWS_PROPS = "amazon-creds.properties";

	/** property that can be listed in creds file or via System */
	public static final String PROP_AWS_ID = "amazon.id";
	/** property that can be listed in creds file or via System */
	public static final String PROP_AWS_SECRET = "amazon.secret";

	/** environment property set by beanstalk console */
	public static final String EVAR_AWS_ID = "AWS_ACCESS_KEY_ID";
	/** environment property set by beanstalk console */
	public static final String EVAR_AWS_SECRET = "AWS_SECRET_KEY";

	//

	private final String awsId;
	private final String awsSecret;

	@Override
	public String getAWSAccessKeyId() {
		return awsId;
	}

	@Override
	public String getAWSSecretKey() {
		return awsSecret;
	}

	//

	private AWSCredentialsLoader(String id, String secret) {
		awsId = id;
		awsSecret = secret;
	}

	private static void log(String text) {
		System.err.println(text);
	}

	public static AWSCredentials load() throws Exception {

		try {
			return fromEnvironmentVariables();
		} catch (Throwable e) {
			log("fromEnvironmentProperties : " + e.getMessage());
		}

		try {
			return fromSystemProperties();
		} catch (Throwable e) {
			log("fromSystemProperties : " + e.getMessage());
		}

		try {
			return fromClasspathProperties();
		} catch (Throwable e) {
			log("fromClasspathProperties : " + e.getMessage());
		}

		try {
			return fromFileProperties();
		} catch (Throwable e) {
			log("fromFileProperties : " + e.getMessage());
		}

		throw new Exception("failed to load aws credentials");

	}

	public static AWSCredentials fromSystemProperties() throws Exception {
		return fromSystemProperties(PROP_AWS_ID, PROP_AWS_SECRET);
	}

	public static AWSCredentials fromSystemProperties(String propId,
			String propSecret) throws Exception {

		String id = System.getProperty(propId);
		if (isInvalid(id)) {
			throw new Exception(//
					"system property missing : " + propId);
		}

		String secret = System.getProperty(propSecret);
		if (isInvalid(secret)) {
			throw new Exception(//
					"system property missing : " + propSecret);
		}

		return new AWSCredentialsLoader(id, secret);

	}

	public static String getDefaultFilePath() {
		return System.getProperty("user.home") + "/.amazon/" + FILE_AWS_PROPS;
	}

	public static AWSCredentials fromFileProperties() throws Exception {
		return fromFileProperties(//
				getDefaultFilePath(), PROP_AWS_ID, PROP_AWS_SECRET);
	}

	public static AWSCredentials fromFileProperties(String filePath,
			String propId, String propSecret) throws Exception {

		Properties properties = new Properties();
		InputStream input = new FileInputStream(filePath);
		properties.load(input);
		input.close();

		String id = properties.getProperty(propId);
		if (isInvalid(id)) {
			throw new Exception(//
					"file property missing : " + propId);
		}

		String secret = properties.getProperty(propSecret);
		if (isInvalid(secret)) {
			throw new Exception(//
					"file property missing : " + propSecret);
		}

		return new AWSCredentialsLoader(id, secret);

	}

	public static AWSCredentials fromClasspathProperties() throws Exception {
		return fromClasspathProperties(//
				FILE_AWS_PROPS, PROP_AWS_ID, PROP_AWS_SECRET);
	}

	public static AWSCredentials fromClasspathProperties(String resource,
			String propId, String propSecret) throws Exception {

		Properties properties = new Properties();
		InputStream input = AWSCredentialsLoader.class.getClassLoader()
				.getResourceAsStream(resource);
		properties.load(input);
		input.close();

		String id = properties.getProperty(propId);
		if (isInvalid(id)) {
			throw new Exception(//
					"class path property missing : " + propId);
		}

		String secret = properties.getProperty(propSecret);
		if (isInvalid(secret)) {
			throw new Exception(//
					"class path property missing : " + propSecret);
		}

		return new AWSCredentialsLoader(id, secret);

	}

	public static AWSCredentials fromEnvironmentVariables() throws Exception {
		return fromEnvironmentVariables(EVAR_AWS_ID, EVAR_AWS_SECRET);
	}

	public static AWSCredentials fromEnvironmentVariables(String propId,
			String propSecret) throws Exception {

		String id = System.getenv(propId);
		if (isInvalid(id)) {
			throw new Exception(//
					"environment varialbe missing : " + propId);
		}

		String secret = System.getenv(propSecret);
		if (isInvalid(secret)) {
			throw new Exception(//
					"environment varialbe missing : " + propSecret);
		}

		return new AWSCredentialsLoader(id, secret);

	}

	private static boolean isInvalid(String value) {
		return value == null || value.length() == 0;
	}

}
