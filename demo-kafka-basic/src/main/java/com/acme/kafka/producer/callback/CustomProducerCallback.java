package com.acme.kafka.producer.callback;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomProducerCallback implements Callback {
	
	private static final Logger LOG = LoggerFactory.getLogger(CustomProducerCallback.class);

	@Override
	public void onCompletion(RecordMetadata metadata, Exception exception) {
		
		if (exception == null) {
        	LOG.info("Received metadata \n" +
                    "Topic: {} \n" +
                    "Partition: {} \n" +
                    "Offset: {} \n" +
                    "Timestamp: {}",
                    metadata.topic(),metadata.partition(), metadata.offset(), metadata.timestamp());
        } else {
        	LOG.error("Error while producing message ", exception);
        }
		
	}

}
