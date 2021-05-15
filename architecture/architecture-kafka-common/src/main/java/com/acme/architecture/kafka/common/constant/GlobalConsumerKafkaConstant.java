package com.acme.architecture.kafka.common.constant;

public final class GlobalConsumerKafkaConstant {

	private GlobalConsumerKafkaConstant() {
	}

	public static final String DEFAULT_CONSUMER_CLIENT_ID = "default-consumer-client";
	
	public static final String DEFAULT_GROUP_ID = "default-group";
	
	public static final String DEFAULT_TOPIC = "default-topic";
	
	public static final Integer DEFAULT_MESSAGE_COUNT = 1000;
	
	public static final Integer DEFAULT_MAX_NO_MESSAGE_FOUND_COUNT = 100;
	
	public static final String OFFSET_RESET_LATEST = "latest";
	
	public static final String OFFSET_RESET_EARLIER = "earliest";
	
	public static final Integer DEFAULT_MAX_POLL_RECORDS = 1;
	
	public static final int DEFAULT_PARTITION_COUNT = 50;
	
	public static final int COUNTDOWNLATCH = 1;
	

}
