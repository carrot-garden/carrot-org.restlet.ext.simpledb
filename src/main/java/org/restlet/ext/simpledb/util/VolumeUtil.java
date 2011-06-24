package org.restlet.ext.simpledb.util;

import java.util.ArrayList;
import java.util.List;

import org.restlet.ext.simpledb.api.Volume;

import com.amazonaws.services.simpledb.AmazonSimpleDB;

public class VolumeUtil {

	public static String getDomain(Volume volume, int index) {

		String prefix = volume.getDomainPrefix();

		String separator = volume.getDomainSeparator();

		String domain = prefix + separator + index;

		return domain;

	}

	public static String getDomain(Volume volume, String item) {

		int count = volume.getDomainCount();

		int index = ShardUtil.getShardIndex(item, count);

		String domain = getDomain(volume, index);

		return domain;

	}

	public static List<String> getDomainList(Volume volume) {

		int count = volume.getDomainCount();

		List<String> list = new ArrayList<String>(count);

		for (int index = 0; index < count; index++) {
			String domain = getDomain(volume, index);
			list.add(domain);
		}

		return list;

	}

	public static void makeDomains(AmazonSimpleDB client, Volume volume)
			throws Exception {

		List<String> list = getDomainList(volume);

		for (String domain : list) {
			SimpleUtil.makeDomain(client, domain);
		}

	}

}
