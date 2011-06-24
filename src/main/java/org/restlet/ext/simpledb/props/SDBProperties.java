package org.restlet.ext.simpledb.props;

import com.amazonaws.auth.AWSCredentials;

public interface SDBProperties extends AWSCredentials {

	String FILE_AWS_PROPS = //
	"amazon-simpledb.properties";

	String HOME_AWS_PROPS = //
	System.getProperty("user.home") + "/.amazon/" + FILE_AWS_PROPS;

	//

	// amazon bean stalk environment variable names
	String PORP_AWS_ID = "AWS_ACCESS_KEY_ID";
	String PROP_AWS_SECRET = "AWS_SECRET_KEY";
	String PROP_CONFIG_DOMAIN = "PARAM1";

	String[] PROPS_ARRAY = new String[] { //
	PORP_AWS_ID, PROP_AWS_SECRET, PROP_CONFIG_DOMAIN };

	//

	String getSDBConfigDomain();

}
