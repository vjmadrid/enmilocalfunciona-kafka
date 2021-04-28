package com.acme.architecture.event.driven.dummy;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.Map;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.acme.architecture.event.driven.constant.ValueDummyGenericEventConstant;
import com.acme.architecture.event.driven.dummy.DummyGenericEvent;
import com.acme.architecture.event.driven.entity.GenericEvent;

public class DummyGenericEventTest {

	@BeforeEach
	public void init() {
	}

	@Test
	public void shouldCreateSampleDefault() {
		assertNotNull(DummyGenericEvent.createSampleDefault());
	}

	@Test
	public void shouldCreateSampleMap() {
		Map<String, GenericEvent> result = DummyGenericEvent.createSampleMap();
		
		assertNotNull(result);
		assertEquals(ValueDummyGenericEventConstant.TEST_NUM_GENERIC_EVENTS,result.size());
	}
	
	@Test
	public void shouldCreateSampleList() {
		List<GenericEvent> result = DummyGenericEvent.createSampleList();
		
		assertNotNull(result);
		assertEquals(ValueDummyGenericEventConstant.TEST_NUM_GENERIC_EVENTS,result.size());
	}

}