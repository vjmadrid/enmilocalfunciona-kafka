package com.acme.model.custom.message.deserializer;

import java.io.Serializable;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.kafka.common.serialization.Deserializer;

public class GenericDeserializer<T extends Serializable> implements Deserializer<T> {

	@Override
	public T deserialize(String topic, byte[] data) {
		return SerializationUtils.deserialize(data);
	}

}
