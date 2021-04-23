package com.acme.kafka.producer.entity;

import java.util.Date;

public class CustomMessage {

	private String value;
	private Date createdDate;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Override
	public String toString() {
		return "Message [value=" + value + ", createdDate=" + createdDate +"]";
	}

}
