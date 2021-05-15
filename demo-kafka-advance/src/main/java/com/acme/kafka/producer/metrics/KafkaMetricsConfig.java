package com.acme.kafka.producer.metrics;

import java.util.HashSet;
import java.util.Set;

public class KafkaMetricsConfig {
	
	private KafkaMetricsConfig() {
		throw new IllegalStateException(this.getClass().getName());
	}
	
	public static Set<String> generateKafkaMetricsNameFilterSet() {
		Set<String> set = new HashSet<String>();
		
		set.add("record-queue-time-avg");
		set.add("record-send-rate");
		set.add("records-per-request-avg");
	
		return set;
	}

}
