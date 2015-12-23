package wiremock;

import com.github.tomakehurst.wiremock.http.Request;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

public class WiremockRequest {

	private Request request;

	private DocumentContext json;

	public WiremockRequest(Request request) {
		this.request = request;
		this.json = new ExceptionFreeReadDocumentContext(JsonPath.parse(request.getBodyAsString()));
	}

	public Request getRequest() {
		return this.request;
	}

	public DocumentContext getJson() {
		return this.json;
	}

}
