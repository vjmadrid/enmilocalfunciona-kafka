package com.acme.kafka.producer.callback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFutureCallback;

public class CustomListenableFutureCallback implements ListenableFutureCallback<SendResult<String, String>> {
	
	private static final Logger LOG = LoggerFactory.getLogger(CustomListenableFutureCallback.class);

	@Override
	public void onSuccess(SendResult<String, String> result) {
		LOG.info("[Callback] Sent message=[{}] with offset=[{}]", result.getProducerRecord().value(), result.getRecordMetadata().offset());
	}

	@Override
	public void onFailure(Throwable ex) {
		LOG.info("[Callback] Unable to send message due to : {}", ex.getMessage());
	}

}
