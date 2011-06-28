package bench.select;

import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Reference;
import org.restlet.routing.Filter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestFilter extends Filter {

	static final Logger log = LoggerFactory.getLogger(TestFilter.class);

	@Override
	protected int doHandle(Request request, Response response) {

		Reference ref = request.getResourceRef();
		log.debug("ref : {}", ref);

		super.handle(request, response);

		return CONTINUE;

	}

}
