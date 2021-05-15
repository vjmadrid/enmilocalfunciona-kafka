package com.acme.model.custom.message.deserializer;

import java.nio.charset.StandardCharsets;

import org.apache.kafka.common.serialization.Deserializer;

import com.acme.model.custom.message.entity.CustomMessage;
import com.acme.model.custom.message.factory.CustomMessageFactory;
import com.fasterxml.jackson.core.JsonProcessingException;

public class CustomMessageJsonDeserializer implements Deserializer<CustomMessage>{
	

	@Override
	public CustomMessage deserialize(String topic, byte[] data) {
		try {
			return CustomMessageFactory.create(new String(data, StandardCharsets.UTF_8));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

}
