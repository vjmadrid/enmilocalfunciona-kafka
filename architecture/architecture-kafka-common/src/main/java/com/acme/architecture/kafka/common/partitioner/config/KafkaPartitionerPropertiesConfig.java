package com.acme.architecture.kafka.common.partitioner.config;

import java.util.Objects;
import java.util.Properties;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acme.architecture.kafka.common.partitioner.CitiesPartitioner;
import com.acme.architecture.kafka.common.partitioner.constant.CitiesPartitionerConstant;

public class KafkaPartitionerPropertiesConfig {
	private static final Logger LOG = LoggerFactory.getLogger(KafkaPartitionerPropertiesConfig.class);

	private KafkaPartitionerPropertiesConfig() {
		throw new IllegalStateException(this.getClass().getName());
	}
	
	public static void setupCitiesPartitioner(Properties kafkaProducerProperties) {
		LOG.info("[KafkaPartitionerPropertiesConfig] *** setupCitiesPartitioner ***");
		Objects.requireNonNull(kafkaProducerProperties);

        // Configure custom for use in the partitioner
        kafkaProducerProperties.put(CitiesPartitionerConstant.PARTITION_0_ID, CitiesPartitionerConstant.PARTITION_0_VALUE_MADRID);
        kafkaProducerProperties.put(CitiesPartitionerConstant.PARTITION_1_ID, CitiesPartitionerConstant.PARTITION_1_VALUE_BARCELONA);
        kafkaProducerProperties.put(CitiesPartitionerConstant.PARTITION_2_ID, CitiesPartitionerConstant.PARTITION_2_VALUE_SEVILLA);
        
		// "partitioner.class" : Set partitioner
		kafkaProducerProperties.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, CitiesPartitioner.class.getCanonicalName());
        
	}

}
