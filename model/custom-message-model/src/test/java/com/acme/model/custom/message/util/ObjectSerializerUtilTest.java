package com.acme.model.custom.message.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.acme.model.custom.message.dummy.DummyCustomMessage;
import com.acme.model.custom.message.entity.CustomMessage;

public class ObjectSerializerUtilTest {

	private CustomMessage customMessageTest;

	@BeforeEach
	public void init() {
		customMessageTest = DummyCustomMessage.createDefault();
	}

	@Test
	public void whenCallToSerializeAndDeserialize_thenReturnCustomMessage() throws IOException, ClassNotFoundException {
		byte[] value = ObjectSerializerUtil.serialize(customMessageTest);
		CustomMessage customMessage = (CustomMessage) ObjectSerializerUtil.deserialize(value);
		
		assertNotNull(value);
		assertEquals(customMessageTest.getId(), customMessage.getId());
		assertEquals(customMessageTest.getMessage(), customMessage.getMessage());
		assertEquals(customMessageTest.getCreatedDate(), customMessage.getCreatedDate());
	}
	
	
	
	
}
