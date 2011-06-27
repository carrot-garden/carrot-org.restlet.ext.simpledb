package org.restlet.ext.simpledb.props;

import org.codehaus.jackson.annotate.JsonProperty;

import com.carrotgarden.utils.json.JSON;

public class SDBVolumesProperties {

	public static final String ITEM_VOLUMES_PROPS = "volumes.properties";

	public static final String PROP_VOLUME = "volume_prefix";
	public static final String PROP_SEPARATOR = "name_separator";

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

	public String getSearchPrefix() {
		return getVolumePrefix() + getVolumeSeparator();
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
