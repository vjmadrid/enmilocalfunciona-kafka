package com.acme.kafka.custom.message.entity;

import java.util.Date;

import com.acme.kafka.custom.message.entity.constant.JsonCustomMessageConstant;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CustomMessage {
	
	@JsonProperty(JsonCustomMessageConstant.FIELD_CUSTOM_MESSAGE_ID)
	private Integer identifier;
	
	@JsonProperty(JsonCustomMessageConstant.FIELD_CUSTOM_MESSAGE_MESSAGE)
	private String message;
	
	@JsonProperty(JsonCustomMessageConstant.FIELD_CUSTOM_MESSAGE_CREATED_DATE)
	private Date createdDate;
	
}
