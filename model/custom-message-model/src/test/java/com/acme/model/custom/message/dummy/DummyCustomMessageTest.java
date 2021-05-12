package com.acme.model.custom.message.dummy;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.acme.model.custom.message.constant.ValueDummyCustomMessageConstant;
import com.acme.model.custom.message.dummy.DummyCustomMessage;
import com.acme.model.custom.message.entity.CustomMessage;

public class DummyCustomMessageTest {

	@BeforeEach
	public void init() {
	}

	@Test
	public void shouldCreateSampleDefault() {
		CustomMessage result = DummyCustomMessage.createDefault();
		
		assertNotNull(result);
		assertEquals(ValueDummyCustomMessageConstant.TEST_CUSTOM_MESSAGE_1_ID, result.getId());
		assertEquals(ValueDummyCustomMessageConstant.TEST_CUSTOM_MESSAGE_1_MESSAGE, result.getMessage());
		assertNotNull(result.getCreatedDate());
	}

}