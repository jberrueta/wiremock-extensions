package wiremock;

import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.common.FileSource;
import com.github.tomakehurst.wiremock.extension.ResponseTransformer;
import com.github.tomakehurst.wiremock.http.QueryParameter;
import com.github.tomakehurst.wiremock.http.Request;
import com.github.tomakehurst.wiremock.http.ResponseDefinition;
import com.google.common.collect.Maps;
import com.jayway.jsonpath.JsonPath;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import joptsimple.internal.Strings;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlaceholderTransformer extends ResponseTransformer {

	private static final Logger LOG = LoggerFactory.getLogger(PlaceholderTransformer.class);

	private static final Pattern PATTERN = Pattern.compile("\\$\\{.*?\\}");

	@Override
	public String name() {
		return "placeholder-transformer";
	}

	@Override
	public boolean applyGlobally() {
		return false;
	}

	@Override
	public ResponseDefinition transform(Request request, ResponseDefinition responseDefinition, FileSource files) {
		System.out.println("Transforming placeholders in " + request.getUrl());
		String responseBody = responseDefinition.getBody();
		if (responseDefinition.getBodyFileName() != null) {
			responseBody = new String(files.getBinaryFileNamed(responseDefinition.getBodyFileName()).readContents(),
					Charset.defaultCharset());
		}
		return ResponseDefinitionBuilder.like(responseDefinition).but().withBody(this.transformResponse(request, responseBody)).build();
	}

	private String transformResponse(Request request, String response) {
		Matcher matcher = PATTERN.matcher(response);
		String requestBody = request.getBodyAsString();
		Map<String, String> placeholders = Maps.newHashMap();
		while (matcher.find()) {
			String placeholder = matcher.group();
			if (!placeholders.containsKey(placeholder)) {
				String value = this.lookupValue(placeholder, request, requestBody);
				System.out.println("Placeholder " + placeholder + " will be replace with value " + value);
				placeholders.put(placeholder, value);
			}
		}
		return StringUtils.replaceEach(response, placeholders.keySet().toArray(new String[] {}),
				placeholders.values().toArray(new String[] {}));
	}

	private String lookupValue(String placeholder, Request request, String requestBody) {
		String property = placeholder.substring(2, placeholder.length() - 1);
		String lookupValue = this.lookupQueryParamValue(property, request);
		if (lookupValue != null) {
			return lookupValue;
		}
		return this.lookupBodyValue(property, requestBody);
	}

	private String lookupQueryParamValue(String property, Request request) {
		QueryParameter queryParameter = request.queryParameter(property);
		if (queryParameter.isPresent()) {
			System.out.println("Placeholder found in query params");
			return Strings.join(queryParameter.values(), ",");
		}
		return null;
	}

	private String lookupBodyValue(String property, String request) {
		String value = JsonPath.read(request, property);
		if (value != null) {
			System.out.println("Placeholder found in body");
			return value;
		}
		return null;
	}

}
