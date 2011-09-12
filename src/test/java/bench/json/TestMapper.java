package bench.json;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.carrotgarden.util.json.JSON;

public class TestMapper {

	static final Logger log = LoggerFactory.getLogger(TestMapper.class);

	public static void main(String[] args) throws Exception {

		ObjectMapper mapper = new ObjectMapper();

		JSON.applyMapperPolicy(mapper);

		// Map<String, String> map = new HashMap<String, String>();
		// map.put("key", "value");
		// String text = mapper.writeValueAsString(map);
		// log.debug("text: {}", text);

		Bean bean = new Bean();

		bean.setValue("important");

		List<String> list = new LinkedList<String>();
		list.add("one");
		list.add("two");
		list.add("three");
		bean.setEntryList(list);

		Set<String> set = new HashSet<String>();
		set.add("1");
		set.add("2");
		set.add("3");
		bean.setEntrySet(set);

		Bean beanlet = new Bean();
		beanlet.setEntryList(list);
		beanlet.setEntrySet(set);
		bean.setBean(beanlet);

		String text = mapper.writeValueAsString(bean);
		log.debug("text: {}", text);

		Map<Object, Object> map = mapper.readValue(text, Map.class);
		log.debug("map: {}", map);

		for (Entry<Object, Object> entry : map.entrySet()) {
			Object key = entry.getKey();
			Object value = entry.getValue();
			log.debug("key: {}", key.getClass().getName());
			log.debug("value: {}", value.getClass().getName());
		}

	}
}
