package com.acme.model.custom.message.entity;

import java.io.Serializable;
import java.util.Date;

import com.acme.model.custom.message.constant.CustomMessageJsonFieldConstant;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CustomMessage implements Serializable {
	
	private static final long serialVersionUID = -8219495645342968666L;

	@JsonProperty(CustomMessageJsonFieldConstant.FIELD_CUSTOM_MESSAGE_ID)
	private Integer id;
	
	@JsonProperty(CustomMessageJsonFieldConstant.FIELD_CUSTOM_MESSAGE_MESSAGE)
	private String message;
	
	@JsonProperty(CustomMessageJsonFieldConstant.FIELD_CUSTOM_MESSAGE_CREATED_DATE)
	private Date createdDate;
	
}
