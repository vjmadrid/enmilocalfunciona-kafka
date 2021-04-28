package com.acme.kafka.testing.constant;

public final class TestingConstant {

	private TestingConstant() {
	}

	public static final String BOOTSTRAP_SERVERS = "localhost:9092";
	
	public static final String TOPIC_1 = "topic-1";
	public static final String TOPIC_2 = "topic-2";
	
	public static final String GROUP_ID = "my-group";
	
	public static final String CONSUMER_GROUP_ID = "consumer-group-1";
	public static final String CONSUMER_AUTO_COMMIT = "false";
	
	public static final int NUM_PARTITIONS = 1;
	
	public static final String MESSAGE_TEMPLATE = "Hello World! %s - %s [%s]";
	public static final String KEY_TEMPLATE = "id_%s";
	
}
