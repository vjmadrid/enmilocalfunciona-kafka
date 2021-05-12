package com.acme.model.custom.message.serializer;

import java.nio.charset.StandardCharsets;

import org.apache.kafka.common.serialization.Serializer;

import com.acme.model.custom.message.entity.CustomMessage;
import com.acme.model.custom.message.util.CustomMessageJsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;

public class CustomMessageJsonSerializer implements Serializer<CustomMessage> {
	
	@Override
	public byte[] serialize(String topic, CustomMessage data) {
		try {
			return CustomMessageJsonUtil.toJson(data).getBytes(StandardCharsets.UTF_8);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return null;

	}

}
