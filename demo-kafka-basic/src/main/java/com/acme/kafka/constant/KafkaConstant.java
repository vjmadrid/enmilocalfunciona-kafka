package com.acme.kafka.constant;

public final class KafkaConstant {

	private KafkaConstant() {
	}

	public static final String DEFAULT_KAFKA_SERVER_URL = "localhost";
	
	public static final int DEFAULT_KAFKA_SERVER_PORT = 9092;
	
	public static final String DEFAULT_KAFKA_BROKERS = DEFAULT_KAFKA_SERVER_URL+":"+DEFAULT_KAFKA_SERVER_PORT;
	
	public static final String BOOTSTRAP_SERVERS = DEFAULT_KAFKA_BROKERS;
	
}
