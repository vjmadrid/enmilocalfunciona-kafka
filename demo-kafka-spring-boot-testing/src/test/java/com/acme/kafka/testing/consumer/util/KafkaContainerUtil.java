package com.acme.kafka.testing.consumer.util;

import java.util.Map;

import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;

public class KafkaContainerUtil {

	private KafkaContainerUtil() {
		throw new IllegalStateException("KafkaContainerUtil");
	}

	public static KafkaMessageListenerContainer<Integer, String> generateContainer(ContainerProperties containerProps) {
		Map<String, Object> props = KafkaConsumerPropertiesUtil.generateKafkaConsumerProperties();
		DefaultKafkaConsumerFactory<Integer, String> cf = new DefaultKafkaConsumerFactory<Integer, String>(props);
		KafkaMessageListenerContainer<Integer, String> container = new KafkaMessageListenerContainer<>(cf,
				containerProps);
		return container;
	}

}
