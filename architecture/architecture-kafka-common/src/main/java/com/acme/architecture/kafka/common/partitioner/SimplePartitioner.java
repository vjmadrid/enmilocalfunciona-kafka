package com.acme.architecture.kafka.common.partitioner;

import java.util.Map;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimplePartitioner implements Partitioner {
	
	private static final Logger LOG = LoggerFactory.getLogger(SimplePartitioner.class);

	@Override
	public void configure(Map<String, ?> configs) {
		LOG.info("[SimplePartitioner] *** configure ***");
	}

	@Override
	public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
		LOG.info("[SimplePartitioner] *** partition ***");
		
		LOG.info("\t [*] topic =[{}]", topic);
		LOG.info("\t [*] key =[{}] hashCode =[{}]", key, key.hashCode());
		
		return Math.abs(key.hashCode() % cluster.partitionCountForTopic(topic));
	}

	@Override
	public void close() {
		LOG.info("[SimplePartitioner] *** close ***");
	}

}
