package com.acme.model.custom.message.dummy;

import com.acme.model.custom.message.constant.ValueDummyCustomMessageConstant;
import com.acme.model.custom.message.entity.CustomMessage;
import com.acme.model.custom.message.factory.CustomMessageFactory;

public class DummyCustomMessage {

	private DummyCustomMessage() {
		throw new IllegalStateException("DummyCustomMessage");
	}

	public static CustomMessage createDefault() {
		return CustomMessageFactory.create(ValueDummyCustomMessageConstant.TEST_CUSTOM_MESSAGE_1_ID, ValueDummyCustomMessageConstant.TEST_CUSTOM_MESSAGE_1_MESSAGE);
	}

}
