package com.acme.model.custom.message.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.acme.model.custom.message.dummy.DummyCustomMessage;
import com.acme.model.custom.message.entity.CustomMessage;
import com.acme.model.custom.message.util.CustomMessageJsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;

public class CustomMessageJsonUtilTest {

	private CustomMessage customMessageTest;

	@BeforeEach
	public void init() {
		customMessageTest = DummyCustomMessage.createDefault();
	}

	@Test
	public void whenCallToJsonWithNull_thenReturnEmpty() throws JsonProcessingException {
		String json = CustomMessageJsonUtil.toJson(null);
		
		assertNotNull(json);
		assertEquals("", json);
	}
	
	@Test
	public void whenCallToJson_thenReturnJson() throws JsonProcessingException {
		String json = CustomMessageJsonUtil.toJson(customMessageTest);
		
		System.out.println(json);
		
		assertNotNull(json);
		assertTrue(json.contains("{\"id\":1,\"message\":\"Hello, Greeting 1!\",\"createdDate\":"));
	}
	
	@Test
	public void whenCallToObjectWithNull_thenReturnNull() throws JsonProcessingException {
		CustomMessage result = CustomMessageJsonUtil.toObject(null);
		
		assertNull(result);
	}
	
	@Test
	public void whenCallToObjectWithEmpty_thenReturnNull() throws JsonProcessingException {
		CustomMessage result = CustomMessageJsonUtil.toObject("");
		
		assertNull(result);
	}
	
	@Test
	public void whenCallToObject_thenReturnCustomMessage() throws JsonProcessingException {
		String jsonTest = CustomMessageJsonUtil.toJson(customMessageTest);
		
		CustomMessage result = CustomMessageJsonUtil.toObject(jsonTest);
		
		assertNotNull(result);
		assertEquals(customMessageTest.getId(), result.getId());
		assertEquals(customMessageTest.getMessage(), result.getMessage());
		assertEquals(customMessageTest.getCreatedDate(), result.getCreatedDate());
	}
}
