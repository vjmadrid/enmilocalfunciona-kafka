package com.acme.model.custom.message.util;

import com.acme.model.custom.message.entity.CustomMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class CustomMessageJsonUtil {
	
	private CustomMessageJsonUtil() {
		throw new IllegalStateException("CustomMessageJsonUtil");
	}

	public static String toJson(CustomMessage customMessage) throws JsonProcessingException {
        String result = "";
        
        if (customMessage != null) {
        	// Creating Object of ObjectMapper (Jakson API)
            ObjectMapper objectMapper = new ObjectMapper();
            
            result = objectMapper.writeValueAsString(customMessage);
        }
        
        return result;
    }
	
	public static CustomMessage toObject(String customMessageJson) throws JsonProcessingException {
		CustomMessage result = null;
        
        if ((customMessageJson != null) && (!customMessageJson.isEmpty())) {
        	// Creating Object of ObjectMapper (Jakson API)
            ObjectMapper objectMapper = new ObjectMapper();
            
            result = objectMapper.readValue(customMessageJson, CustomMessage.class);
        }
        
        return result;
    }

}