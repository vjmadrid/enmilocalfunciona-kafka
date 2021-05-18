package com.acme.model.custom.message.serializer;

import java.util.Map;

import org.apache.kafka.common.serialization.Serializer;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CustomMessageSerializer implements Serializer<Object> {

	@Override
    public void configure(Map map, boolean b) {
    }
	
	@Override
	public byte[] serialize(String topic, Object data) {
		byte[] retVal = null;
        ObjectMapper objectMapper = new ObjectMapper();
        
        try {
            retVal = objectMapper.writeValueAsBytes(data);
        } catch (Exception e) {
            System.err.println("Exception: " + e);
        }
        return retVal;
	}
	
	@Override
    public void close() {

    }

}
