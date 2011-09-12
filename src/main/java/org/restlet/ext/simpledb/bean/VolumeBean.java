package org.restlet.ext.simpledb.bean;

import org.codehaus.jackson.annotate.JsonProperty;
import org.restlet.ext.simpledb.api.Volume;
import org.restlet.ext.simpledb.util.ShardUtil;

import com.carrotgarden.util.json.JSON;

public class VolumeBean extends DomainBean implements Volume {

	@JsonProperty(PROP_FORMAT)
	private String domainFormat;

	@JsonProperty(PROP_SEPARATOR)
	private String nameSeparator;

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
	public String getDomainFormat() {
		return domainFormat;
	}

	@Override
	public String getNameSeparator() {
		return nameSeparator;
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

	private String[] domainNames;

	@Override
	public String getDomainName(int index) {
		if (index < 0 || index >= domainCount) {
			return "invalid-domain-index";
		}
		if (domainNames == null) {
			String[] namesArray = new String[domainCount];
			for (int k = 0; k < domainCount; k++) {
				namesArray[k] = String.format(domainFormat, k);
			}
			domainNames = namesArray;
		}
		return domainNames[index];
	}

	// @Override
	private String getEntryPrefix(String entry) {

		int index = entry.indexOf(nameSeparator);

		if (index <= 0) {
			return entry;
		} else {
			return entry.substring(index);
		}

	}

	@Override
	public String getDomainName(String entry) {

		String prefix = getEntryPrefix(entry);

		int index = ShardUtil.getShardIndex(prefix, domainCount);

		return getDomainName(index);

	}

}
