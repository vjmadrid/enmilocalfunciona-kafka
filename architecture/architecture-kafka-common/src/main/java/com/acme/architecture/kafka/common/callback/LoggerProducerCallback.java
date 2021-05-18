package com.acme.architecture.kafka.common.callback;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acme.architecture.kafka.common.constant.GlobalKafkaTemplateConstant;

public class LoggerProducerCallback implements Callback {
	
	private static final Logger LOG = LoggerFactory.getLogger(LoggerProducerCallback.class);
	
	private long startTime;
    private String key;
    private String message;
    
    public LoggerProducerCallback(long startTime, String key, String message) {
        this.startTime = startTime;
        this.key = key;
        this.message = message;
    }

	@Override
	public void onCompletion(RecordMetadata metadata, Exception exception) {
		// Define send execution time
		long elapsedTime = System.currentTimeMillis() - startTime;
		
		LOG.info(GlobalKafkaTemplateConstant.TEMPLATE_LOG_PRODUCER_CALLBACK_RECEIVED_DATA, this.key, this.message);
		
		if (exception == null) {
			String message = String.format(GlobalKafkaTemplateConstant.TEMPLATE_STRING_PRODUCER_CALLBACK_RECEIVED_METADA, metadata.topic(), metadata.partition(), metadata.offset(), metadata.timestamp(), (elapsedTime / 1000));
			
        	LOG.info(message);
        } else {
        	String errorMessage = String.format(GlobalKafkaTemplateConstant.TEMPLATE_STRING_PRODUCER_CALLBACK_ERROR, metadata, exception);
  
        	LOG.error(errorMessage);
        	exception.printStackTrace();
        }
		
	}

}
