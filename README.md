# Wiremock extensions

## Extensions list

* [Placeholder transformer](#placeholder-transformer)

## Placeholder transformer

Allows specifying placeholders in the mapped response that will be replaced based on the values of the different query parameters or body properties.


### Usage

```
java -cp "wiremock-extensions-1.0.jar:wiremock-1.57-standalone.jar" com.github.tomakehurst.wiremock.standalone.WireMockServerRunner --extensions wiremock.PlaceholderTransformer
```

### Example

#### Mapping
```
{
	"priority": 1,
	"request": {
		"method": "POST",
		"urlPath": "/some-url"
	},
	"response": {
		"status": 200,
		"headers": {
			"Content-Type": "application/json",
			"Accept": "application/json"
		},
		"status": 200,
		"body": "{ \"value\": \"${someValue}\" }",
		"transformers": ["placeholder-transformer"]
	}
}
```

#### Request (with query string parameter)

```
POST /some-url?someValue=123
Request: { }
```

#### Response

```
{ "value": "123" }
```

#### Request (with post property)

```
POST /some-url
Request: { "someValue": "456" }
```

#### Response

```
{ "value": "456" }
```

### Reference

The extension will replace the placeholder with its value no matter if the response body is set literally (using Wiremock's "body" property) or via a "bodyFileName".

If a placeholder references a property present both as a query string parameter and a body attribute, the value used for the replacement will be the one sent as a query param.
