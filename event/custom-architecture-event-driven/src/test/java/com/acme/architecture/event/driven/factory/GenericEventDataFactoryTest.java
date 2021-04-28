package com.acme.architecture.event.driven.factory;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.acme.architecture.event.driven.constant.ValueDummyGenericEventConstant;
import com.acme.architecture.event.driven.entity.GenericEvent;

public class GenericEventDataFactoryTest {

	@BeforeEach
	public void init() {
	}

	@Test
	public void shouldCreate() {
		GenericEvent result = GenericEventDataFactory.create(ValueDummyGenericEventConstant.TEST_GENERIC_EVENT_1_ID,ValueDummyGenericEventConstant.TEST_GENERIC_EVENT_1_PARENT_ID, ValueDummyGenericEventConstant.TEST_GENERIC_EVENT_1_NAME,ValueDummyGenericEventConstant.TEST_GENERIC_EVENT_1_TYPE, ValueDummyGenericEventConstant.TEST_GENERIC_EVENT_1_AUTHOR, ValueDummyGenericEventConstant.TEST_GENERIC_EVENT_1_EXPIRATION_SECONDS, ValueDummyGenericEventConstant.TEST_GENERIC_EVENT_1_PAYLOAD);
		
		assertNotNull(result);
		assertEquals(ValueDummyGenericEventConstant.TEST_GENERIC_EVENT_1_ID,result.getId());
		assertEquals(ValueDummyGenericEventConstant.TEST_GENERIC_EVENT_1_PARENT_ID,result.getParentId());
		
		assertNotNull(result.getCreatedDate());
		assertNull(result.getUpdatedDate());
		assertNull(result.getDeletedDate());
	}
	
	@Test
	public void shouldCreateWithLong() {
		GenericEvent result = GenericEventDataFactory.create(1,2, ValueDummyGenericEventConstant.TEST_GENERIC_EVENT_1_NAME,ValueDummyGenericEventConstant.TEST_GENERIC_EVENT_1_TYPE, ValueDummyGenericEventConstant.TEST_GENERIC_EVENT_1_AUTHOR, ValueDummyGenericEventConstant.TEST_GENERIC_EVENT_1_EXPIRATION_SECONDS, ValueDummyGenericEventConstant.TEST_GENERIC_EVENT_1_PAYLOAD);
		
		assertNotNull(result);
		assertEquals("1",result.getId());
		assertEquals("2",result.getParentId());
		
		assertNotNull(result.getCreatedDate());
		assertNull(result.getUpdatedDate());
		assertNull(result.getDeletedDate());
	}

}