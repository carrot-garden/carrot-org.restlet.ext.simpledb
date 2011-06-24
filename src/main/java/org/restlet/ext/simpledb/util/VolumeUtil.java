package org.restlet.ext.simpledb.util;

import org.restlet.ext.simpledb.api.Volume;

public class VolumeUtil {

	public static String getDomain(Volume volume, String item) {

		String prefix = volume.getDomainPrefix();

		int count = volume.getDomainCount();

		int index = ShardUtil.getShardIndex(item, count);

		String domain = prefix + "|" + index;

		return domain;

	}

}
