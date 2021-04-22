package com.acme.kafka.producer.constant;

public final class DemoConstant {

	private DemoConstant() {
	}

	public static final String BOOTSTRAP_SERVERS = "localhost:9092";
	public static final String TOPIC = "topic-1";
    
    public static final int NUM_MESSAGES = 10;
    public static final String MESSAGE_TEMPLATE = "Hello World! %s - %s";
    public static final int NUM_SECONDS_DELAY_MESSAGE = 2;
    
    public static final String KEY_TEMPLATE = "id_%s";
    
    public static final String GROUP_ID = "my-group";

}
