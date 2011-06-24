package org.restlet.ext.simpledb.bean;

import org.codehaus.jackson.annotate.JsonProperty;
import org.restlet.ext.simpledb.api.Volume;

import com.carrotgarden.utils.json.JSON;

public class VolumeBean extends DomainBean implements Volume {

	public static final String PROP_PREFIX = "domain.prefix";
	public static final String PROP_COUNT = "domain.count";
	public static final String PROP_ACTIVE = "is.active";
	public static final String PROP_URI_ID = "uri.id";

	//

	@JsonProperty(PROP_PREFIX)
	private String domainPrefix;

	@JsonProperty(PROP_COUNT)
	private int domainCount;

	@JsonProperty(PROP_ACTIVE)
	private boolean isActive;

	@JsonProperty(PROP_URI_ID)
	private String uriId;

	//

	@Override
	public String toString() {
		return JSON.intoText(this);
	}

	//

	@Override
	public String getDomainPrefix() {
		return domainPrefix;
	}

	@Override
	public int getDomainCount() {
		return domainCount;
	}

	@Override
	public boolean isActive() {
		return isActive;
	}

	@Override
	public String getUriId() {
		return uriId;
	}

}
