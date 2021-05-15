package com.acme.model.custom.message.serializer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.acme.model.custom.message.deserializer.CustomMessageJsonDeserializer;
import com.acme.model.custom.message.dummy.DummyCustomMessage;
import com.acme.model.custom.message.entity.CustomMessage;
import com.fasterxml.jackson.core.JsonProcessingException;

public class CustomMessageJsonSerializerDeserializerTest {

	byte[] customMessageByteArrayTest = null;
	
	private CustomMessageJsonSerializer customMessageJsonSerializer;
	
	private CustomMessageJsonDeserializer customMessageJsonDeserializer;
	
	private CustomMessage customMessageTest;

	@BeforeEach
	public void init() throws IOException {
		customMessageJsonSerializer = new CustomMessageJsonSerializer();
		customMessageJsonDeserializer = new CustomMessageJsonDeserializer();
		
		customMessageTest = DummyCustomMessage.createDefault();
	}

	@Test
	public void whenCallToSerializeAndDeserialize_thenReturnCustomMessage() throws JsonProcessingException {
		byte[] valueByteArray = customMessageJsonSerializer.serialize("", customMessageTest);
		CustomMessage valueObject = customMessageJsonDeserializer.deserialize("", valueByteArray);

		assertNotNull(valueObject);
		assertEquals(customMessageTest.getId(), valueObject.getId());
		assertEquals(customMessageTest.getMessage(), valueObject.getMessage());
		assertEquals(customMessageTest.getCreatedDate(), valueObject.getCreatedDate());
	}
}
