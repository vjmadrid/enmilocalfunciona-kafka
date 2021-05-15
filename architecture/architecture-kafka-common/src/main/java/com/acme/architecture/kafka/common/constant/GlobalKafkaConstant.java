package com.acme.architecture.kafka.common.constant;

public final class GlobalKafkaConstant {

	private GlobalKafkaConstant() {
	}

	public static final String DEFAULT_KAFKA_SERVER_URL = "localhost";
	public static final int DEFAULT_KAFKA_SERVER_PORT = 9092;
	
	public static final String DEFAULT_KAFKA_BROKERS = DEFAULT_KAFKA_SERVER_URL+":"+DEFAULT_KAFKA_SERVER_PORT;
	public static final String DEFAULT_BOOTSTRAP_SERVERS = DEFAULT_KAFKA_BROKERS;
	
}
