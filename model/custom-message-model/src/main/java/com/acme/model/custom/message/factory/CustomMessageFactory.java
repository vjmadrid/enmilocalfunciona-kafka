package com.acme.model.custom.message.factory;

import java.util.Date;

import com.acme.model.custom.message.entity.CustomMessage;
import com.acme.model.custom.message.util.CustomMessageJsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;

public class CustomMessageFactory {
	
	protected CustomMessageFactory() {
		throw new IllegalStateException("CustomMessageFactory");
	}

	public static CustomMessage create(Integer id, String message) {
		final CustomMessage customMessage = new CustomMessage();
		
		customMessage.setId(id);
		customMessage.setMessage(message);
		customMessage.setCreatedDate(new Date());
		
		return customMessage;
	}
	
	public static CustomMessage create(Integer id, String message, Date newCreatedDate) {
		final CustomMessage customMessage = create(id, message);
		
		customMessage.setCreatedDate(newCreatedDate);
		
		return customMessage;
	}
	
	public static CustomMessage create(final String customMessageJson) throws JsonProcessingException {
		return CustomMessageJsonUtil.toObject(customMessageJson);
	}

}
