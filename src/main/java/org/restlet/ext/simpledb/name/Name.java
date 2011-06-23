package org.restlet.ext.simpledb.name;

public interface Name {

	interface Id {

		String REGION = "region";
		String ENTRY = "entry";

		String DOMAIN = "domain";
		String ITEM = "item";
		String ATTRIBUTE = "attribute";

	}

	String NONE = "";

	String ROOT = "/";

	// via domain sharding

	/** first part of composite id used for sharding */
	String COMPOSITE_ID_SEPARATOR = "|";

	String REGIONS = //
	"/regions";

	String REGION = //
	"/regions/{region}";

	String ENTRIES = //
	"/regions/{region}/entries";

	/* {entry} == firstId|secondId|thirdId */
	String ENTRY = //
	"/regions/{region}/entries/{entry}";

	// via direct domain access

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
