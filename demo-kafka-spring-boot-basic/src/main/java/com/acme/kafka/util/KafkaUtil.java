package com.acme.kafka.util;

import org.slf4j.Logger;
import org.springframework.messaging.MessageHeaders;

public class KafkaUtil {
	
	private KafkaUtil() {
		throw new IllegalStateException("KafkaUtil");
	}

	public static void showMessageHeaders(MessageHeaders headers, Logger LOG) {
		if (headers != null) {
			headers.keySet().forEach(key -> LOG.info("\t{}: {}", key, headers.get(key)));
		}
	}

}