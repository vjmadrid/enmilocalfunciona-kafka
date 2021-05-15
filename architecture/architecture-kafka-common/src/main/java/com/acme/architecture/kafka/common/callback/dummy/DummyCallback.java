package com.acme.architecture.kafka.common.callback.dummy;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DummyCallback implements Callback {

	public static final Logger LOG = LoggerFactory.getLogger(DummyCallback.class);
	
	@Override
	public void onCompletion(RecordMetadata recordMetadata, Exception e) {
		if (e != null) {
			LOG.error("Error while producing message to topic : {}", recordMetadata.topic(), e);
        } else
        	LOG.debug("sent message to topic:{} partition:{}  offset:{}", recordMetadata.topic(), recordMetadata.partition(), recordMetadata.offset());
	}

}
