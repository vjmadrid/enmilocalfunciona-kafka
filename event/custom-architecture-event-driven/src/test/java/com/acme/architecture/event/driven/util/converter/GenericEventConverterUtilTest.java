package com.acme.architecture.event.driven.util.converter;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.acme.architecture.event.driven.dummy.DummyGenericEvent;
import com.acme.architecture.event.driven.entity.GenericEvent;

public class GenericEventConverterUtilTest {

	private GenericEvent genericEventTest;
	
	private final String PART_JSON_CONTAINS = "{\"id\":\"1\",\"parentId\":\"";

	@BeforeEach
	public void init() {
		genericEventTest = DummyGenericEvent.createSampleDefault();
	}
	
	@Test
	public void shouldCreateDefaultConstructor_ThenTrowIllegalStateException() {

		assertThrows(IllegalStateException.class, () -> {
			new GenericEventConverterUtil();
		});
	}
	
	@Test
	public void whenCallAConvertGenericEventToJsonWithNull_thenReturnNull() throws Exception {
		assertNull(GenericEventConverterUtil.convertGenericEventToJson(null));
	}

	@Test
	public void whenCallAConvertGenericEventToJson_thenReturnObjectAsJson() throws Exception {
		String resultJSON = GenericEventConverterUtil.convertGenericEventToJson(genericEventTest);
		
		assertNotNull(resultJSON);
		assertTrue(resultJSON.contains(PART_JSON_CONTAINS));
	}

	@Test
	public void whenCallAConvertJsonToGenericEventWithNull_thenReturnNull() throws Exception {
		assertNull(GenericEventConverterUtil.fromJsonToGenericEvent(null));
	}
	
	@Test
	public void whenCallAConvertJsonToGenericEventWithEmpty_thenReturnNull() throws Exception {
		assertNull(GenericEventConverterUtil.fromJsonToGenericEvent(""));
	}

	
	@Test
	public void whenCallAConvertJsonToObject_thenReturnObject() throws Exception {
		String resultJSON = GenericEventConverterUtil.convertGenericEventToJson(genericEventTest);
	
		GenericEvent resultObject = GenericEventConverterUtil.fromJsonToGenericEvent(resultJSON);
		
		assertNotNull(resultObject);
		assertEquals(genericEventTest,resultObject);
	}

}