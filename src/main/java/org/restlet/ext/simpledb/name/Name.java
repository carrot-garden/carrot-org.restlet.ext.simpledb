package org.restlet.ext.simpledb.name;

public interface Name {

	interface Id {
		String DOMAIN = "domain";
		String ITEM = "item";
		String ATTRIBUTE = "attribute";
	}

	String NONE = "";

	String ROOT = "/";

	//

	String REGIONS = //
	"/regions";

	String REGION = //
	"/regions/{region}";

	String ENTRIES = //
	"/regions/{region}/entries";

	String ENTRY = //
	"/regions/{region}/entries/{entry}";

	//

	String DOMAINS = //
	"/domains";

	String DOMAIN = //
	"/domains/{domain}";

	String ITEMS = //
	"/domains/{domain}/items";

	String ITEM = //
	"/domains/{domain}/items/{item}";

	String ATTRIBUTES = //
	"/domains/{domain}/items/{item}/attributes";

	String ATTRIBUTE = //
	"/domains/{domain}/items/{item}/attributes/{attribute}";

}
