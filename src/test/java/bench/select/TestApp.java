package bench.select;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Filter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestApp extends Application {

	static final Logger log = LoggerFactory.getLogger(TestApp.class);

	TestApp() {
	}

	@Override
	public Restlet createOutboundRoot() {

		Filter next0 = (Filter) super.createOutboundRoot();
		log.debug("next0 : {}", next0);

		Restlet next1 = next0.getNext();

		log.debug("next1 : {}", next1);

		TestFilter filter = new TestFilter();

		filter.setNext(next0);

		return filter;

	}

}
