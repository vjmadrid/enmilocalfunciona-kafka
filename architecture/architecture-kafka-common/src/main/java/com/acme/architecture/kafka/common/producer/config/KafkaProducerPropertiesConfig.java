package com.acme.architecture.kafka.common.producer.config;

import java.util.Objects;
import java.util.Properties;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acme.architecture.kafka.common.producer.interceptor.CustomProducerInterceptor;

public class KafkaProducerPropertiesConfig {

	private static final Logger LOG = LoggerFactory.getLogger(KafkaProducerPropertiesConfig.class);
	
	private KafkaProducerPropertiesConfig() {
		throw new IllegalStateException(this.getClass().getName());
	}

	public static void setupClientIdAndBootstrap(Properties kafkaProducerProperties, String idProducer, String brokers) {
		LOG.info("[KafkaProducerPropertiesConfig] *** setupClientIdAndBootstrap ***");
		Objects.requireNonNull(kafkaProducerProperties);
		Objects.requireNonNull(idProducer);
		Objects.requireNonNull(brokers);

		// "bootstrap.servers" : Set "initial" server / broker connections (list of broker addresses) -> host/port pairs with comma separated list
		kafkaProducerProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, brokers);

		// "client.id" : Set client id (uniquely identifier) -> tracking the source of requests
		kafkaProducerProperties.put(ProducerConfig.CLIENT_ID_CONFIG, idProducer);
	}
	
	public static void setupSerializerStringKeyStringValue(Properties kafkaProducerProperties) {
		LOG.info("[KafkaProducerPropertiesConfig] *** setupSerializerStringKeyStringValue ***");
		Objects.requireNonNull(kafkaProducerProperties);

		// "key.serializer" : Set key serializer class -> StringSerializer for String
		kafkaProducerProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		//kafkaProducerProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
		
		// "value.serializer": Set value serializer class -> StringSerializer for String
		kafkaProducerProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		//kafkaProducerProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer"); 
	}
	
	public static void setupSerializerLongKeyStringValue(Properties kafkaProducerProperties) {
		LOG.info("[KafkaProducerPropertiesConfig] *** setupSerializerLongKeyStringValue ***");
		Objects.requireNonNull(kafkaProducerProperties);

		// "key.serializer" : Set key serializer class -> LongSerializer for Long
		kafkaProducerProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class.getName());
		
		// "value.serializer": Set value serializer class -> StringSerializer for String
		kafkaProducerProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
	}
	
	public static void setupBasic(Properties kafkaProducerProperties) {
		LOG.info("[KafkaProducerPropertiesConfig] *** setupBasic ***");
		Objects.requireNonNull(kafkaProducerProperties);
		
		// "retries" : Set xxx
		kafkaProducerProperties.put(ProducerConfig.RETRIES_CONFIG, 0);
		
		// "acks" : Set durability -> configure number of acknowledgments(criteria under which requests are considered complete)
		//
		// 	Note :
		//		Configure in-sync replicas : min.insync.replicas in broker configuration and/or topic configuration
		//
		// 	- Default "all" (all in-sync replicas) : The leader to only give successful ack after all followers ack the send was written to their log
		//	- Value "1" (only wait for leader) : The leader give successful ack after write their log -> No wait for the followers (replicas)
		//  - Value "0" (no wait) : 
		kafkaProducerProperties.put(ProducerConfig.ACKS_CONFIG, "all");
		
		// "buffer.memory" : total bytes of memory the producer can use to buffer records waiting to be sent to the Broker
		//		- If records are sent faster than broker can handle than the producer blocks -> Used for compression and in-flight records
		kafkaProducerProperties.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
		
		// "max.block.ms" : Set control time producer blocks before throwing BufferExhaustedException
		kafkaProducerProperties.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, 1000);
	}
	
	public static void setupBatching(Properties kafkaProducerProperties) {
		LOG.info("[KafkaProducerPropertiesConfig] *** setupBatching ***");
		Objects.requireNonNull(kafkaProducerProperties);
		
		// "batch.size" : Set record size in bytes -> 64K buffer sizes
		//	 - 0 disable batching
		//   - Other Values : 16384, 32768
		kafkaProducerProperties.put(ProducerConfig.BATCH_SIZE_CONFIG,  16_384 * 4);

		// "linger.ms" : Set how much to wait for other records before sending the batch -> 50 ms
		kafkaProducerProperties.put(ProducerConfig.LINGER_MS_CONFIG, 50);
	}
	
	public static void setupBatchComprension(Properties kafkaProducerProperties) {
		LOG.info("[KafkaProducerPropertiesConfig] *** setupBatchComprension ***");
		Objects.requireNonNull(kafkaProducerProperties);

		// "compression.type" : Set compression for batch -> "snappy"
		kafkaProducerProperties.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "snappy");
	}
	
	public static void setupRetriesInFlightTimeout(Properties kafkaProducerProperties) {
		LOG.info("[KafkaProducerPropertiesConfig] *** setupRetriesInFlightTimeout ***");
		Objects.requireNonNull(kafkaProducerProperties);

		// "max.in.flight.requests.per.connection" : Set Only one in-flight messages per Kafka broker connection (default 5) -> 1
		kafkaProducerProperties.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, 1);

        // "retries" : Set the number of retries -> 5 retries
		kafkaProducerProperties.put(ProducerConfig.RETRIES_CONFIG, 5);

        // "request.timeout.ms" : Set request timeout -> 10 seconds 
		kafkaProducerProperties.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, 10_000);

        // "retry.backoff.ms" : Set retry after ms -> 1 second
		kafkaProducerProperties.put(ProducerConfig.RETRY_BACKOFF_MS_CONFIG, 1_000);
	}
	
	public static void setupInterceptors(Properties kafkaProducerProperties) {
		LOG.info("[KafkaProducerPropertiesConfig] *** setupInterceptors ***");
		Objects.requireNonNull(kafkaProducerProperties);
		
		// "interceptor.classes" : Set producer interceptors list
		kafkaProducerProperties.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG, CustomProducerInterceptor.class.getName());
	}

}
