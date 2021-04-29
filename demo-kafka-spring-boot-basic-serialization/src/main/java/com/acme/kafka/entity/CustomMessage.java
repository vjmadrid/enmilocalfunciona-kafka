package com.acme.kafka.entity;

import com.acme.kafka.entity.constant.JsonCustomMessageConstant;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data 
public class CustomMessage {
	
	@JsonProperty(JsonCustomMessageConstant.FIELD_CUSTOM_MESSAGE_ID)
	private int identifier;
	
	@JsonProperty(JsonCustomMessageConstant.FIELD_CUSTOM_MESSAGE_MESSAGE)
	private String message;
	
}
