package com.acme.kafka.constant;

public final class KafkaConstant {

	private KafkaConstant() {
	}

	public static final String DEFAULT_KAFKA_SERVER_URL = "localhost";
	public static final int DEFAULT_KAFKA_SERVER_PORT = 9092;
	
	public static final String DEFAULT_KAFKA_BROKERS = DEFAULT_KAFKA_SERVER_URL+":"+DEFAULT_KAFKA_SERVER_PORT;
	public static final String DEFAULT_BOOTSTRAP_SERVERS = DEFAULT_KAFKA_BROKERS;
	
	public static final String DEFAULT_PRODUCER_CLIENT_ID = "default-producer-client";
	public static final String DEFAULT_CONSUMER_CLIENT_ID = "default-consumer-client";
	
	public static final String DEFAULT_GROUP_ID = "default-group";
	public static final String DEFAULT_TOPIC = "default-topic";
	public static final Integer DEFAULT_MESSAGE_COUNT = 1000;
	
}
