package org.restlet.ext.simpledb.props;

import java.util.Properties;

import org.restlet.ext.simpledb.util.PropsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SDBPropertiesLoader implements SDBProperties {

	private static final Logger log = LoggerFactory
			.getLogger(SDBPropertiesLoader.class);

	//

	private final Properties properties;

	private SDBPropertiesLoader(Properties props) {
		properties = props;
	}

	public static SDBProperties load() throws Exception {

		try {
			return fromEnvironmentVariables();
		} catch (Throwable e) {
			log.warn("fromEnvironmentVariables : " + e.getMessage());
		}

		try {
			return fromSystemProperties();
		} catch (Throwable e) {
			log.warn("fromSystemProperties : " + e.getMessage());
		}

		try {
			return fromClasspathProperties();
		} catch (Throwable e) {
			log.warn("fromClasspathProperties : " + e.getMessage());
		}

		try {
			return fromFilepathProperties();
		} catch (Throwable e) {
			log.warn("fromFilepathProperties : " + e.getMessage());
		}

		throw new Exception("failed to load aws credentials");

	}

	private static SDBProperties fromEnvironmentVariables() throws Exception {
		return new SDBPropertiesLoader(//
				PropsUtil.fromEnvironmentVariables(PROPS_ARRAY));
	}

	private static SDBProperties fromSystemProperties() throws Exception {
		return new SDBPropertiesLoader(//
				PropsUtil.fromSystemProperties(PROPS_ARRAY));
	}

	private static SDBProperties fromClasspathProperties() throws Exception {
		return new SDBPropertiesLoader(//
				PropsUtil.fromClassPath(FILE_AWS_PROPS, PROPS_ARRAY));
	}

	private static SDBProperties fromFilepathProperties() throws Exception {
		return new SDBPropertiesLoader(//
				PropsUtil.fromFilePath(HOME_AWS_PROPS, PROPS_ARRAY));
	}

	//

	@Override
	public String getAWSAccessKeyId() {
		return properties.getProperty(PORP_AWS_ID);
	}

	@Override
	public String getAWSSecretKey() {
		return properties.getProperty(PROP_AWS_SECRET);
	}

	@Override
	public String getSDBConfigDomain() {
		return properties.getProperty(PROP_CONFIG_DOMAIN);
	}

}
