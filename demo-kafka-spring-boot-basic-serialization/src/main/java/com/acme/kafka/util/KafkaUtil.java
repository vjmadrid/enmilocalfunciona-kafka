package com.acme.kafka.util;

import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;

public class KafkaUtil {
	
	private KafkaUtil() {
		throw new IllegalStateException("KafkaUtil");
	}

	public static void showMessageHeaders(MessageHeaders headers, Logger LOG) {
		if (headers != null) {
			headers.keySet().forEach(key -> LOG.info("\t{}: {}", key, headers.get(key)));
		}
	}
	
//	public String typeIdHeader(Headers headers) {
//        return StreamSupport.stream(headers.spliterator(), false)
//                .filter(header -> header.key().equals("__TypeId__"))
//                .findFirst().map(header -> new String(header.value())).orElse("N/A");
//    }

}