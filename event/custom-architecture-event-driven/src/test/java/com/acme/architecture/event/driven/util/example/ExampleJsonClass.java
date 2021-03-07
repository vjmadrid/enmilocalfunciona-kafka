package com.acme.architecture.event.driven.util.example;

import java.io.Serializable;

public class ExampleJsonClass implements Serializable {

	private static final long serialVersionUID = -8219001645698314203L;
	
	private String field1 = "1";
	private Integer field2 = 2;

	public String getField1() {
		return field1;
	}

	public Integer getField2() {
		return field2;
	}

	public void setField1(String field1) {
		this.field1 = field1;
	}

	public void setField2(Integer field2) {
		this.field2 = field2;
	}
	
}

