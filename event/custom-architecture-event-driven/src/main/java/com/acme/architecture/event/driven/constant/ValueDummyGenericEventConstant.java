package com.acme.architecture.event.driven.constant;

import com.acme.architecture.event.driven.enumerate.GenericEventTypeEnum;

public final class ValueDummyGenericEventConstant {
	
	private ValueDummyGenericEventConstant() {
	}
	
	public static final int TEST_NUM_GENERIC_EVENTS = 3;
	
	public static final String TEST_GENERIC_EVENT_1_ID = "1";
	public static final String TEST_GENERIC_EVENT_1_PARENT_ID = "";
	public static final String TEST_GENERIC_EVENT_1_NAME = "Test Name 1";
	public static final String TEST_GENERIC_EVENT_1_TYPE = GenericEventTypeEnum.CREATE.toString();
	public static final String TEST_GENERIC_EVENT_1_AUTHOR = "Test ACME Author";
	public static final Long TEST_GENERIC_EVENT_1_EXPIRATION_SECONDS = 0L;
	public static final String TEST_GENERIC_EVENT_1_PAYLOAD = "Test Message 1";
	
	public static final String TEST_GENERIC_EVENT_2_ID = "2";
	public static final String TEST_GENERIC_EVENT_2_PARENT_ID = "";
	public static final String TEST_GENERIC_EVENT_2_NAME = "Test Name 2";
	public static final String TEST_GENERIC_EVENT_2_TYPE = GenericEventTypeEnum.UPDATE.toString();
	public static final String TEST_GENERIC_EVENT_2_AUTHOR = "Test ACME Author";
	public static final Long TEST_GENERIC_EVENT_2_EXPIRATION_SECONDS = 0L;
	public static final String TEST_GENERIC_EVENT_2_PAYLOAD = "Test Message 2";
	
	public static final String TEST_GENERIC_EVENT_3_ID = "3";
	public static final String TEST_GENERIC_EVENT_3_PARENT_ID = "2";
	public static final String TEST_GENERIC_EVENT_3_NAME = "Test Name 3";
	public static final String TEST_GENERIC_EVENT_3_TYPE = GenericEventTypeEnum.DELETE.toString();
	public static final String TEST_GENERIC_EVENT_3_AUTHOR = "Test ACME Author";
	public static final Long TEST_GENERIC_EVENT_3_EXPIRATION_SECONDS = 120L;
	public static final String TEST_GENERIC_EVENT_3_PAYLOAD = "Test Message 3";
	

}
