package org.restlet.ext.simpledb.util;

import org.restlet.ext.simpledb.api.Volume;

import com.amazonaws.services.simpledb.AmazonSimpleDB;

public class VolumeUtil {

	public static void makeDomains(AmazonSimpleDB client, Volume volume)
			throws Exception {

		for (int index = 0; index < volume.getDomainCount(); index++) {
			String domain = volume.getDomainName(index);
			SimpleUtil.makeDomain(client, domain);
		}

	}

}
