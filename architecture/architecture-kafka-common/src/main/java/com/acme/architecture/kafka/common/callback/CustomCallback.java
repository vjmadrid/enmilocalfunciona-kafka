package com.acme.architecture.kafka.common.callback;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomCallback implements Callback {
	
	public static final Logger LOG = LoggerFactory.getLogger(CustomCallback.class);
	
	private final static String FORMAT_SENT_MESSAGE = "[CustomCallback] Sent record(key={} value=[{}]) to topic= {} with  meta(partition= {}, offset= {}) time= {}\n";
	
	private final long startTime;
	
    private final int key;
    
    private final String message;
	
    public CustomCallback(long startTime, int key, String message) {
        this.startTime = startTime;
        this.key = key;
        this.message = message;
    }
    
	@Override
	public void onCompletion(RecordMetadata recordMetadata, Exception exception) {
		long elapsedTime = System.currentTimeMillis() - startTime;
        if (recordMetadata != null) {
        	LOG.info(FORMAT_SENT_MESSAGE,key,message,recordMetadata.topic(), recordMetadata.partition(),recordMetadata.offset(), elapsedTime);
        } else {
            exception.printStackTrace();
        }
		
	}

}
