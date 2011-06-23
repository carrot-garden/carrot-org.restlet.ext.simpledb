package org.restlet.ext.simpledb.util;

import java.nio.charset.Charset;

public class ShardUtil {

	private static final long HASH_MINIM = Integer.MIN_VALUE;
	private static final long HASH_RANGE = Integer.MAX_VALUE - HASH_MINIM;

	public static int getShardIndex(String itemName, int shardCount) {
		return getShardIndexJOATT(itemName, shardCount);
	}

	// XXX works but slow
	public static int getShardIndexJOATT(String itemName, int shardCount) {

		if (shardCount <= 1) {
			return 0;
		}

		long shardRange = HASH_RANGE / shardCount;
		long hash = hashCodeJOATT(itemName);
		long absoluteHash = hash - HASH_MINIM;

		return (int) (absoluteHash / shardRange);
	}

	private static final Charset UTF_8 = Charset.forName("UTF-8");

	public static int hashCodeJOATT(String text) {

		int hash = 0;

		byte[] array = text.getBytes(UTF_8);

		for (byte b : array) {
			hash += (b & 0xFF);
			hash += (hash << 10);
			hash ^= (hash >>> 6);
		}

		hash += (hash << 3);
		hash ^= (hash >>> 11);
		hash += (hash << 15);

		return hash;

	}

	public static int hashCodeSDBM(String text) {

		int hash = 0;

		int size = text.length();

		for (int k = 0; k < size; k++) {
			hash = text.charAt(k) + (hash << 6) + (hash << 16) - hash;
		}

		return hash;
	}

	// XXX fast but broken
	public static int getShardIndexDJB2(String itemName, int shardCount) {

		if (shardCount <= 1) {
			return 0;
		}

		int hash = hashCodeDJB2(itemName);

		return Math.abs(hash) % shardCount;

	}

	public static int hashCodeDJB2(String text) {

		int hash = 5381;

		int size = text.length();

		for (int k = 0; k < size; k++) {
			hash = ((hash << 5) + hash) + text.charAt(k);
		}

		return hash;
	}

}
