package com.acme.model.custom.message.factory;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.acme.model.custom.message.constant.ValueDummyCustomMessageConstant;
import com.acme.model.custom.message.entity.CustomMessage;
import com.acme.model.custom.message.factory.CustomMessageFactory;

public class CustomMessageFactoryTest {

	@BeforeEach
	public void init() {
	}

	@Test
	public void shouldCreate() {
		CustomMessage result = CustomMessageFactory.create(ValueDummyCustomMessageConstant.TEST_CUSTOM_MESSAGE_1_ID, ValueDummyCustomMessageConstant.TEST_CUSTOM_MESSAGE_1_MESSAGE);
		
		assertNotNull(result);
		assertEquals(ValueDummyCustomMessageConstant.TEST_CUSTOM_MESSAGE_1_ID, result.getId());
		assertEquals(ValueDummyCustomMessageConstant.TEST_CUSTOM_MESSAGE_1_MESSAGE, result.getMessage());
		assertNotNull(result.getCreatedDate());
	}
	
	@Test
	public void shouldCreateWithCreatedDate() {
		Date newCreatedDateTest =  new Date();
		CustomMessage result = CustomMessageFactory.create(ValueDummyCustomMessageConstant.TEST_CUSTOM_MESSAGE_1_ID, ValueDummyCustomMessageConstant.TEST_CUSTOM_MESSAGE_1_MESSAGE, newCreatedDateTest);
		
		assertNotNull(result);
		assertEquals(ValueDummyCustomMessageConstant.TEST_CUSTOM_MESSAGE_1_ID, result.getId());
		assertEquals(ValueDummyCustomMessageConstant.TEST_CUSTOM_MESSAGE_1_MESSAGE, result.getMessage());
		assertNotNull(result.getCreatedDate());
		assertEquals(newCreatedDateTest, result.getCreatedDate());
	}

}