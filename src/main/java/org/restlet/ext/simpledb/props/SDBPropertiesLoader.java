package org.restlet.ext.simpledb.props;

public class SDBPropertiesLoader implements SDBProperties {

	//

	private SDBPropertiesLoader() {

	}

	public static SDBProperties load() throws Exception {
		return new SDBPropertiesLoader();
	}

	@Override
	public String getSDBConfigDomain() {
		return null;
	}

	@Override
	public String getSDBVolumePrefix() {
		return null;
	}

	@Override
	public String getAWSAccessKeyId() {
		return null;
	}

	@Override
	public String getAWSSecretKey() {
		return null;
	}

}
