package org.restlet.ext.aws;

import java.io.FileInputStream;
import java.util.Properties;

import com.amazonaws.auth.AWSCredentials;

public class AWSCredentialsLoader implements AWSCredentials {

	/** file that stores aws id & secret */
	public static final String FILE_AWS_PROPS = "aws-credentials.properties";

	/** property that can be listed in creds file or via System */
	public static final String PROP_AWS_ID = "restlet.aws.id";
	/** property that can be listed in creds file or via System */
	public static final String PROP_AWS_SECRET = "restlet.aws.secret";

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
			return loadSystemProperties();
		} catch (Exception e) {
			log("failed to loadSystemProperties : " + e.getMessage());
		}

		try {
			return loadFileProperties();
		} catch (Exception e) {
			log("failed to loadFileProperties : " + e.getMessage());
		}

		try {
			return loadEnvironmentProperties();
		} catch (Exception e) {
			log("failed to loadEnvironmentProperties : " + e.getMessage());
		}

		throw new Exception("failed to load aws credentials");

	}

	private static AWSCredentials loadSystemProperties() throws Exception {

		String id = System.getProperty(PROP_AWS_ID);
		if (isInvalid(id)) {
			throw new Exception(//
					"system property missing : " + PROP_AWS_ID);
		}

		String secret = System.getProperty(PROP_AWS_SECRET);
		if (isInvalid(secret)) {
			throw new Exception(//
					"system property missing : " + PROP_AWS_SECRET);
		}

		return new AWSCredentialsLoader(id, secret);

	}

	private static AWSCredentials loadFileProperties() throws Exception {

		Properties properties = new Properties();
		FileInputStream in = new FileInputStream(FILE_AWS_PROPS);
		properties.load(in);
		in.close();

		String id = properties.getProperty(PROP_AWS_ID);
		if (isInvalid(id)) {
			throw new Exception(//
					"system property missing : " + PROP_AWS_ID);
		}

		String secret = properties.getProperty(PROP_AWS_SECRET);
		if (isInvalid(secret)) {
			throw new Exception(//
					"system property missing : " + PROP_AWS_SECRET);
		}

		return new AWSCredentialsLoader(id, secret);

	}

	private static AWSCredentials loadEnvironmentProperties() throws Exception {

		String id = System.getenv(EVAR_AWS_ID);
		if (isInvalid(id)) {
			throw new Exception(//
					"environment varialbe missing : " + EVAR_AWS_ID);
		}

		String secret = System.getenv(EVAR_AWS_SECRET);
		if (isInvalid(secret)) {
			throw new Exception(//
					"environment varialbe missing : " + EVAR_AWS_SECRET);
		}

		return new AWSCredentialsLoader(id, secret);

	}

	private static boolean isInvalid(String value) {
		return value == null || value.length() == 0;
	}

}
