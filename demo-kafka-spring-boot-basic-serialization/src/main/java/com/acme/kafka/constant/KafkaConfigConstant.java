package com.acme.kafka.constant;

public final class KafkaConfigConstant {

	private KafkaConfigConstant() {
	}
	
	public static final String RECEIVER_GROUP_ID_CONFIG = "example-group";
	public static final String RECEIVER_AUTO_OFFSET_RESET_CONFIG = "earliest";
	public static final int RECEIVER_COUNTDOWNLATCH = 1;
	
}
