package com.acme.kafka.producer.callback;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProducerCallback implements Callback {
	
	private static final Logger LOG = LoggerFactory.getLogger(ProducerCallback.class);
	
	private long startTime;
    private String key;
    private String message;
    
    public ProducerCallback(long startTime, String key, String message) {
        this.startTime = startTime;
        this.key = key;
        this.message = message;
    }

	@Override
	public void onCompletion(RecordMetadata metadata, Exception exception) {
		// Define send execution time
		long elapsedTime = System.currentTimeMillis() - startTime;
		
		LOG.info("[ProducerCallback] Received metadata \n" +
                "\tKey: {} \n" +
                "\tMessage: {} \n", this.key, this.message);
		
		if (exception == null) {
        	LOG.info("[ProducerCallback] Received metadata \n" +
                    "\tTopic: {} \n" +
                    "\tPartition: {} \n" +
                    "\tOffset: {} \n" +
                    "\tTimestamp: {}",
                    "\tElapsed Time: {} seconds",
                    metadata.topic(), metadata.partition(), metadata.offset(), metadata.timestamp(), (elapsedTime / 1000));
        } else {
        	LOG.error("[ProducerCallback] Error while producing message ", exception);
        	exception.printStackTrace();
        }
		
	}

}
