package org.restlet.ext.simpledb.util;

import static org.junit.Assert.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestShardUtil {

	static final Logger log = LoggerFactory.getLogger(TestShardUtil.class);

	static final String NAME = "abc-def-ghi-";

	static final int NUM = 33;

	static final int COUNT = 1 * 1000 * 1000;

	static void arrayStats(int[] array) {

		log.debug("array : {}", array);

		int size = array.length;

		int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE, sum = 0;

		for (int k = 0; k < size; k++) {
			int val = array[k];
			min = Math.min(min, val);
			max = Math.max(max, val);
			sum += val;
		}

		int range = max - min;

		int averge = sum / size;

		log.debug("average : {}  range : {}", averge, range);

	}

	static void testGetShardIndexJOATT() {

		int[] countArray = new int[NUM];

		long start = System.nanoTime();

		for (int k = 0; k < COUNT; k++) {
			String name = NAME + k;
			int index = ShardUtil.getShardIndexJOATT(name, NUM);
			// log.debug("index : {}", index);
			countArray[index]++;
		}

		long finish = System.nanoTime();

		arrayStats(countArray);

		log.debug("nanos per index : {}", (finish - start) / COUNT);

		assertTrue(true);
	}

	static void testGetShardIndexDJB2() {

		int[] countArray = new int[NUM];

		long start = System.nanoTime();

		for (int k = 0; k < COUNT; k++) {
			String name = NAME + k;
			int index = ShardUtil.getShardIndexDJB2(name, NUM);
			// log.debug("index : {}", index);
			countArray[index]++;
		}

		long finish = System.nanoTime();

		arrayStats(countArray);

		log.debug("nanos per index : {}", (finish - start) / COUNT);

		assertTrue(true);

	}

	public static void main(String... args) {

		for (int k = 0; k < 3; k++) {
			log.debug("##############");
			testGetShardIndexJOATT();
			testGetShardIndexDJB2();
		}

	}

}
