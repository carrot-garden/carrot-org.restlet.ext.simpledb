package org.restlet.ext.simpledb.props;

import org.codehaus.jackson.annotate.JsonProperty;

import com.carrotgarden.utils.json.JSON;

public class SDBRestletProperties {

	public static final String ITEM_RESTLET_PROPS = "restlet.properties";

	public static final String PROP_VOLUME = "volume.prefix";
	public static final String PROP_SEPARATOR = "volume.separator";

	//

	@JsonProperty(PROP_VOLUME)
	private String volumePrefix;

	@JsonProperty(PROP_SEPARATOR)
	private String volumeSeparator;

	//

	@Override
	public String toString() {
		return JSON.intoText(this);
	}

	//

	public String getVolumePrefix() {
		return volumePrefix;
	}

	public void setVolumePrefix(String volumePrefix) {
		this.volumePrefix = volumePrefix;
	}

	public String getVolumeSeparator() {
		return volumeSeparator;
	}

	public void setVolumeSeparator(String volumeSeparator) {
		this.volumeSeparator = volumeSeparator;
	}

}
