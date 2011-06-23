package bench.shard;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestSimpleDBUtils {

	static final Logger log = LoggerFactory.getLogger(TestSimpleDBUtils.class);

	public static void main(String... args) {

		log.debug("start");

		int num = 10;

		int[] countArray = new int[num];

		for (int k = 0; k < 1000 * 1000; k++) {
			String name = "abc-def-" + k;
			int index = SimpleDBUtils.getShardIndex(name, num);
			// log.debug("index : {}", index);
			countArray[index]++;
		}

		log.debug("countArray : {}", countArray);

	}

}
