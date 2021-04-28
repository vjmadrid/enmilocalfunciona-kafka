package com.acme.architecture.event.driven.util.converter;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

public final class JsonConverterUtil {

	protected JsonConverterUtil() {
		throw new IllegalStateException("JsonConverterUtil");
	}

	public static String convertObjectToJson(Object object, boolean activePretty ) throws JsonProcessingException {

		if (object != null) {
			if (activePretty) {
				return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(object);
			} 
			
			return new ObjectMapper().writeValueAsString(object);
		}
		
		return null;
	}
	
	public static String convertObjectToJsonDefault(Object object) throws JsonProcessingException {
		return JsonConverterUtil.convertObjectToJson(object,Boolean.FALSE);
	}

	public static String convertJsonToYaml(String jsonString) throws IOException {

		if ((jsonString == null) || "".equals(jsonString)) {
			return null;
		}

		// parse JSON
		JsonNode jsonNodeTree = new ObjectMapper().readTree(jsonString);
		// save it as YAML
		return new YAMLMapper().writeValueAsString(jsonNodeTree);
	}
	
	public static Object convertJsonToObject(final String jsonString , Class<?> valueClass) throws JsonParseException, JsonMappingException, IOException {
		
		if (((jsonString == null) || "".equals(jsonString)) || (valueClass == null)) {
			return null;
		}
		
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(jsonString, valueClass); 
	}

}