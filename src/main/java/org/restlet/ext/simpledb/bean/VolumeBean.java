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

	private String getEntryPrefix(final String entry) {

		final int separatorIndex = entry.indexOf(nameSeparator);

		if (separatorIndex <= 0) {
			return entry;
		} else {
			return entry.substring(0, separatorIndex);
		}

	}

	@Override
	public String getDomainName(final String entry) {

		final int shardIndex;

		if (entry == null) {

			shardIndex = 0;

		} else {

			final String prefix = getEntryPrefix(entry);

			shardIndex = ShardUtil.getShardIndex(prefix, domainCount);

		}

		return getDomainName(shardIndex);

	}

}
