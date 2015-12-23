package wiremock;

import com.jayway.jsonpath.PathNotFoundException;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.EvaluationListener;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Predicate;
import com.jayway.jsonpath.ReadContext;
import com.jayway.jsonpath.TypeRef;

public class ExceptionFreeReadDocumentContext implements com.jayway.jsonpath.DocumentContext {

	private com.jayway.jsonpath.DocumentContext documentContext;

	public ExceptionFreeReadDocumentContext(com.jayway.jsonpath.DocumentContext documentContext) {
		this.documentContext = documentContext;
	}

	public Configuration configuration() {
		return this.documentContext.configuration();
	}

	public <T> T json() {
		return this.documentContext.json();
	}

	public String jsonString() {
		return this.documentContext.jsonString();
	}

	public <T> T read(String path, Predicate... filters) {
		try {
			return this.documentContext.read(path, filters);
		} catch (PathNotFoundException exception) {
			return null;
		} catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	public com.jayway.jsonpath.DocumentContext set(String path, Object newValue, Predicate... filters) {
		return this.documentContext.set(path, newValue, filters);
	}

	public <T> T read(String path, Class<T> type, Predicate... filters) {
		try {
			return this.documentContext.read(path, type, filters);
		} catch (PathNotFoundException exception) {
			return null;
		} catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	public com.jayway.jsonpath.DocumentContext set(JsonPath path, Object newValue) {
		return this.documentContext.set(path, newValue);
	}

	public com.jayway.jsonpath.DocumentContext delete(String path, Predicate... filters) {
		return this.documentContext.delete(path, filters);
	}

	public <T> T read(JsonPath path) {
		try {
			return this.documentContext.read(path);
		} catch (PathNotFoundException exception) {
			return null;
		} catch (Exception exception) {
			return null;
		}
	}

	public <T> T read(JsonPath path, Class<T> type) {
		try {
			return this.documentContext.read(path, type);
		} catch (PathNotFoundException exception) {
			return null;
		} catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	public com.jayway.jsonpath.DocumentContext delete(JsonPath path) {
		return this.documentContext.delete(path);
	}

	public com.jayway.jsonpath.DocumentContext add(String path, Object value, Predicate... filters) {
		return this.documentContext.add(path, value, filters);
	}

	public <T> T read(JsonPath path, TypeRef<T> typeRef) {
		try {
			return this.documentContext.read(path, typeRef);
		} catch (PathNotFoundException exception) {
			return null;
		} catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	public <T> T read(String path, TypeRef<T> typeRef) {
		try {
			return this.documentContext.read(path, typeRef);
		} catch (PathNotFoundException exception) {
			return null;
		} catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	public com.jayway.jsonpath.DocumentContext add(JsonPath path, Object value) {
		return this.documentContext.add(path, value);
	}

	public com.jayway.jsonpath.DocumentContext put(String path, String key, Object value, Predicate... filters) {
		return this.documentContext.put(path, key, value, filters);
	}

	public ReadContext limit(int maxResults) {
		return this.documentContext.limit(maxResults);
	}

	public ReadContext withListeners(EvaluationListener... listener) {
		return this.documentContext.withListeners(listener);
	}

	public com.jayway.jsonpath.DocumentContext put(JsonPath path, String key, Object value) {
		return this.documentContext.put(path, key, value);
	}

}
