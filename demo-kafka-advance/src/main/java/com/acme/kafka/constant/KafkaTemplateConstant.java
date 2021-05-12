package com.acme.kafka.constant;

public final class KafkaTemplateConstant {

	private KafkaTemplateConstant() {
	}
	
    public static final String TEMPLATE_KEY = "id_%s";
    
    public static final String TEMPLATE_LOG_CONSUMER_RECORDS = "[*] Check records -> \n" + 
    		"\tRecord Count: {} \n" + 
    		"\tPartition Count: {}";
    
    public static final String TEMPLATE_LOG_CONSUMER_RECORD = "[*] Received record \n" +
			"\tKey: {} \n" +
			"\tValue: {} \n" +
            "\tTopic: {} \n" +
            "\tPartition: {}\n" +
            "\tOffset: {} \n" +
            "\tTimestamp: {}";
    
    public static final String TEMPLATE_LOG_CONSUMER_RECORD_FOR_THREAD = "[*] Received record with ThreadId=[{}] \n" +
			"\tKey: {} \n" +
			"\tValue: {} \n" +
            "\tTopic: {} \n" +
            "\tPartition: {}\n" +
            "\tOffset: {} \n" +
            "\tTimestamp: {}";
    
    public static final String TEMPLATE_LOG_PRODUCER_CALLBACK_RECEIVED_DATA = "[Callback] Received data \n" +
            "\tKey: {} \n" +
            "\tMessage: {} \n";
    
    public static final String TEMPLATE_LOG_PRODUCER_CALLBACK_RECEIVED_METADA = "[Callback] Received metadata \n" +
            "\tTopic: {} \n" +
            "\tPartition: {} \n" +
            "\tOffset: {} \n" +
            "\tTimestamp: {} \n" +
            "\tElapsed Time: {} seconds";
    
    public static final String TEMPLATE_LOG_PRODUCER_CALLBACK_ERROR = "[Callback] Error while producing message exception={}";

    public static final String TEMPLATE_LOG_RECORD_METADATA = "[*] Received metadata \n" +
            "\tTopic: {} \n" +
            "\tPartition: {} \n" +
            "\tOffset: {} \n" +
            "\tTimestamp: {}";
	
}
