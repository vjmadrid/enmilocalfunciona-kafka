package com.acme.model.custom.message.validator;

import com.acme.model.custom.message.entity.CustomMessage;

public final class CustomMessageValidatorUtil {
	
	private CustomMessageValidatorUtil() {
		throw new IllegalStateException("CustomMessageValidatorUtil");
	}
	
	public static boolean isNull(CustomMessage customMessage) {
		return (customMessage == null);
	}

	public static boolean isNotNull(CustomMessage customMessage) {
		return (customMessage != null);
	}

	public static boolean isValid(CustomMessage customMessage) {
		return (isNotNull(customMessage) && customMessage.getId() != null);
	}

}
