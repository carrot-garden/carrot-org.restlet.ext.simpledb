package bench.counter;

import org.codehaus.jackson.map.ObjectMapper;
import org.restlet.ext.simpledb.props.SDBPropertiesLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.simpledb.AmazonSimpleDB;
import com.amazonaws.services.simpledb.AmazonSimpleDBClient;
import com.carrotgarden.util.json.JSON;

public class TestCounter {

	static final Logger log = LoggerFactory.getLogger(TestCounter.class);

	public static void main(String[] args) throws Exception {

		AWSCredentials creds = SDBPropertiesLoader.load();

		AmazonSimpleDB sdb = new AmazonSimpleDBClient(creds);

		log.debug("sdb : {}", sdb);

		ObjectMapper mapper = JSON.getInstance();

	}

}
