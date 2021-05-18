package com.acme.model.custom.message.deserializer;

import java.util.Map;

import org.apache.kafka.common.serialization.Deserializer;

import com.acme.model.custom.message.entity.CustomMessage;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CustomMessageDeserializer implements Deserializer<Object> {

	@Override
    public void configure(Map map, boolean b) {
    }
	
	@Override
	public Object deserialize(String topic, byte[] data) {
		ObjectMapper mapper = new ObjectMapper();
		CustomMessage obj = null;

        try {
            obj = mapper.readValue(data, CustomMessage.class);
        } catch (Exception e) {
            System.err.println("Exception: " + e);
        }

        return obj;
	}
	
	@Override
    public void close() {
        // Nothing to close
    }

}
