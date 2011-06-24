package org.restlet.ext.simpledb.props;

import com.amazonaws.auth.AWSCredentials;

public interface SDBProperties extends AWSCredentials {

	String FILE_AWS_PROPS = "amazon-simpledb.properties";

	String HOME_AWS_PROPS = System.getProperty("user.home") + "/.amazon/"
			+ FILE_AWS_PROPS;

	//

	// file or system props
	String PROP_AWS_ID = "amazon.id";
	String PROP_AWS_SECRET = "amazon.secret";
	String PROP_AWS_DOMAIN = "amazon.simple.config";

	// beanstalk environment
	String EVAR_AWS_ID = "AWS_ACCESS_KEY_ID";
	String EVAR_AWS_SECRET = "AWS_SECRET_KEY";
	String EVAR_AWS_DOMAIN = "PARAM1";

	//

	String ITEM_COMPOSITE_ID_SEPARATOR = "|";

	//

	String getSDBConfigDomain();

	String getSDBVolumePrefix();

}
