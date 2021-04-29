package com.acme.architecture.event.driven.dummy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.acme.architecture.event.driven.constant.ValueDummyGenericEventConstant;
import com.acme.architecture.event.driven.entity.GenericEvent;
import com.acme.architecture.event.driven.factory.GenericEventDataFactory;

public final class DummyGenericEvent {

	private DummyGenericEvent() {
		throw new IllegalStateException("DummyGenericEvent");
	}
	
	public static GenericEvent createSampleDefault() {
		return GenericEventDataFactory.create(ValueDummyGenericEventConstant.TEST_GENERIC_EVENT_1_ID,ValueDummyGenericEventConstant.TEST_GENERIC_EVENT_1_PARENT_ID, ValueDummyGenericEventConstant.TEST_GENERIC_EVENT_1_NAME,ValueDummyGenericEventConstant.TEST_GENERIC_EVENT_1_TYPE, ValueDummyGenericEventConstant.TEST_GENERIC_EVENT_1_AUTHOR, ValueDummyGenericEventConstant.TEST_GENERIC_EVENT_1_EXPIRATION_SECONDS, ValueDummyGenericEventConstant.TEST_GENERIC_EVENT_1_PAYLOAD);
	}
	
	public static Map<String,GenericEvent> createSampleMap() {
		final Map<String,GenericEvent> map = new HashMap<>(); 
		map.put(ValueDummyGenericEventConstant.TEST_GENERIC_EVENT_1_ID, GenericEventDataFactory.create(ValueDummyGenericEventConstant.TEST_GENERIC_EVENT_1_ID,ValueDummyGenericEventConstant.TEST_GENERIC_EVENT_1_PARENT_ID, ValueDummyGenericEventConstant.TEST_GENERIC_EVENT_1_NAME,ValueDummyGenericEventConstant.TEST_GENERIC_EVENT_1_TYPE, ValueDummyGenericEventConstant.TEST_GENERIC_EVENT_1_AUTHOR, ValueDummyGenericEventConstant.TEST_GENERIC_EVENT_1_EXPIRATION_SECONDS, ValueDummyGenericEventConstant.TEST_GENERIC_EVENT_1_PAYLOAD));
		map.put(ValueDummyGenericEventConstant.TEST_GENERIC_EVENT_2_ID, GenericEventDataFactory.create(ValueDummyGenericEventConstant.TEST_GENERIC_EVENT_2_ID,ValueDummyGenericEventConstant.TEST_GENERIC_EVENT_2_PARENT_ID, ValueDummyGenericEventConstant.TEST_GENERIC_EVENT_2_NAME,ValueDummyGenericEventConstant.TEST_GENERIC_EVENT_2_TYPE, ValueDummyGenericEventConstant.TEST_GENERIC_EVENT_2_AUTHOR, ValueDummyGenericEventConstant.TEST_GENERIC_EVENT_2_EXPIRATION_SECONDS, ValueDummyGenericEventConstant.TEST_GENERIC_EVENT_2_PAYLOAD));
		map.put(ValueDummyGenericEventConstant.TEST_GENERIC_EVENT_3_ID, GenericEventDataFactory.create(ValueDummyGenericEventConstant.TEST_GENERIC_EVENT_3_ID,ValueDummyGenericEventConstant.TEST_GENERIC_EVENT_3_PARENT_ID, ValueDummyGenericEventConstant.TEST_GENERIC_EVENT_3_NAME,ValueDummyGenericEventConstant.TEST_GENERIC_EVENT_3_TYPE, ValueDummyGenericEventConstant.TEST_GENERIC_EVENT_3_AUTHOR, ValueDummyGenericEventConstant.TEST_GENERIC_EVENT_3_EXPIRATION_SECONDS, ValueDummyGenericEventConstant.TEST_GENERIC_EVENT_3_PAYLOAD));		
		return map;
	}
	
	public static List<GenericEvent> createSampleList() {
		final List<GenericEvent> list = new ArrayList<>();
		list.add(GenericEventDataFactory.create(ValueDummyGenericEventConstant.TEST_GENERIC_EVENT_1_ID,ValueDummyGenericEventConstant.TEST_GENERIC_EVENT_1_PARENT_ID, ValueDummyGenericEventConstant.TEST_GENERIC_EVENT_1_NAME,ValueDummyGenericEventConstant.TEST_GENERIC_EVENT_1_TYPE, ValueDummyGenericEventConstant.TEST_GENERIC_EVENT_1_AUTHOR, ValueDummyGenericEventConstant.TEST_GENERIC_EVENT_1_EXPIRATION_SECONDS, ValueDummyGenericEventConstant.TEST_GENERIC_EVENT_1_PAYLOAD));
		list.add(GenericEventDataFactory.create(ValueDummyGenericEventConstant.TEST_GENERIC_EVENT_2_ID,ValueDummyGenericEventConstant.TEST_GENERIC_EVENT_2_PARENT_ID, ValueDummyGenericEventConstant.TEST_GENERIC_EVENT_2_NAME,ValueDummyGenericEventConstant.TEST_GENERIC_EVENT_2_TYPE, ValueDummyGenericEventConstant.TEST_GENERIC_EVENT_2_AUTHOR, ValueDummyGenericEventConstant.TEST_GENERIC_EVENT_2_EXPIRATION_SECONDS, ValueDummyGenericEventConstant.TEST_GENERIC_EVENT_2_PAYLOAD));
		list.add(GenericEventDataFactory.create(ValueDummyGenericEventConstant.TEST_GENERIC_EVENT_3_ID,ValueDummyGenericEventConstant.TEST_GENERIC_EVENT_3_PARENT_ID, ValueDummyGenericEventConstant.TEST_GENERIC_EVENT_3_NAME,ValueDummyGenericEventConstant.TEST_GENERIC_EVENT_3_TYPE, ValueDummyGenericEventConstant.TEST_GENERIC_EVENT_3_AUTHOR, ValueDummyGenericEventConstant.TEST_GENERIC_EVENT_3_EXPIRATION_SECONDS, ValueDummyGenericEventConstant.TEST_GENERIC_EVENT_3_PAYLOAD));
		return list;
	}
	
}