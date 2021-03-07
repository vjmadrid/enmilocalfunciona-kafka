package com.acme.architecture.event.driven.util.converter;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.acme.architecture.event.driven.util.example.ExampleJsonClass;

public final class JsonConverterUtilTest {

	private String EXAMPLE_JSON = "{" + System.lineSeparator() + "  \"field1\" : \"1\"," + System.lineSeparator()
			+ "  \"field2\" : 2" + System.lineSeparator() + "}";
	
	private String EXAMPLE_JSON_BASIC = "{\"field1\":\"1\",\"field2\":2}";

	private String EXAMPLE_YAML = "---" + System.lineSeparator() + "field1: \"1\"" + System.lineSeparator()
			+ "field2: 2" + System.lineSeparator();

	private String EMPTY_RESULT = "";

	private String NEW_LINE_WINDOWS = "\r\n";

	private String NEW_LINE_LINUX = "\n";
	
	@Test
	public void shouldCreateDefaultConstructor_ThenTrowIllegalStateException() {

		assertThrows(IllegalStateException.class, () -> {
			new JsonConverterUtil();
		});
	}
	
	@Test
	public void whenCallAConvertObjectToJsonWithNull_thenReturnNull() throws Exception {
		assertNull(JsonConverterUtil.convertObjectToJson(null, false));
	}

	@Test
	public void whenCallAConvertObjectToJson_thenReturnObjectAsJson() throws Exception {
		assertEquals(EXAMPLE_JSON_BASIC, JsonConverterUtil.convertObjectToJson(new ExampleJsonClass(), false));
	}
	
	@Test
	public void whenCallAConvertObjectToJsonPretty_thenReturnObjectAsJson() throws Exception {
		assertNotEquals(EXAMPLE_JSON.replaceAll(NEW_LINE_LINUX,NEW_LINE_WINDOWS), JsonConverterUtil.convertObjectToJson(new ExampleJsonClass(), true));
	}
	
	
	@Test
	public void whenCallAConvertObjectToJsonDefault_thenReturnObjectAsJson() throws Exception {
		assertEquals(EXAMPLE_JSON_BASIC, JsonConverterUtil.convertObjectToJsonDefault(new ExampleJsonClass()));
	}

	@Test
	public void whenCallAConvertJsonToYamlWithNull_thenReturnNull() throws Exception {
		String resultJSON = JsonConverterUtil.convertJsonToYaml(null);

		assertNull(resultJSON);
	}

	@Test
	public void whenCallAConvertJsonToYamlWithEmpty_thenReturnNull() throws Exception {
		String resultJSON = JsonConverterUtil.convertJsonToYaml(EMPTY_RESULT);

		assertNull(resultJSON);
	}

	@Test
	public void whenCallAConvertJsonToYaml_thenReturnYaml() throws Exception {
		String resultJSON = JsonConverterUtil
				.convertJsonToYaml(EXAMPLE_JSON.replaceAll(NEW_LINE_WINDOWS, NEW_LINE_LINUX));

		assertEquals(EXAMPLE_YAML.replaceAll(NEW_LINE_WINDOWS, NEW_LINE_LINUX), resultJSON);
	}
	
	@Test
	public void whenCallAConvertJsonToObjectWithNull_thenReturnNull() throws Exception {
		assertNull(JsonConverterUtil.convertJsonToObject(null, ExampleJsonClass.class));
	}
	
	@Test
	public void whenCallAConvertJsonToObjectWithEmpty_thenReturnNull() throws Exception {
		assertNull(JsonConverterUtil.convertJsonToObject("", ExampleJsonClass.class));
	}
	
	@Test
	public void whenCallAConvertJsonToObjectWithNullClass_thenReturnNull() throws Exception {
		String resultJSON = JsonConverterUtil
				.convertJsonToYaml(EXAMPLE_JSON.replaceAll(NEW_LINE_WINDOWS, NEW_LINE_LINUX));
		
		assertNull(JsonConverterUtil.convertJsonToObject(resultJSON, null));
	}
	
	@Test
	public void whenCallAConvertJsonToObject_thenReturnObject() throws Exception {
		String resultJSON = JsonConverterUtil.convertObjectToJsonDefault(new ExampleJsonClass());
	
		ExampleJsonClass resultObject = (ExampleJsonClass) JsonConverterUtil.convertJsonToObject(resultJSON, ExampleJsonClass.class);
		
		assertNotNull(resultObject);
		assertEquals("1", resultObject.getField1());
		assertEquals(2, resultObject.getField2());
	}

}

