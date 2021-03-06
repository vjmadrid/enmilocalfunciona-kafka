package com.acme.kafka.consumer;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.Node;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acme.architecture.kafka.common.constant.GlobalConsumerKafkaConstant;
import com.acme.architecture.kafka.common.constant.GlobalKafkaConstant;
import com.acme.architecture.kafka.common.constant.GlobalKafkaTemplateConstant;
import com.acme.architecture.kafka.common.consumer.config.KafkaConsumerConfig;
import com.acme.architecture.kafka.common.util.KafkaPropertiesUtil;
import com.acme.kafka.constant.DemoConstant;

/**
 * 	Receives a set of messages defined as "String" performing "poll" every certain time (2 seconds)
 *  
 *  Use specific partitions
 *  
 *  Asynchronous
 *  
 * 	No message limit
 *  
 *  ENABLE_AUTO_COMMIT_CONFIG = True
 *  
 *  Different producers can be used
 *   - Java producer with appropriate configuration
 *   - kafka-console-producer.sh --broker-list localhost:9092 --topic topic-1
 * 
 */

public class AppConsumerWithPartitionsAssignment {

    private static final Logger LOG = LoggerFactory.getLogger(AppConsumerWithPartitionsAssignment.class);
    
    public static void main(String[] args) throws InterruptedException {
    	
    	LOG.info("*** Init ***");
    	
    	// Create consumer properties
        Properties kafkaConsumerProperties = KafkaConsumerConfig.consumerConfigsStringKeyStringValue(GlobalConsumerKafkaConstant.DEFAULT_CONSUMER_CLIENT_ID, GlobalKafkaConstant.DEFAULT_BOOTSTRAP_SERVERS, GlobalConsumerKafkaConstant.DEFAULT_GROUP_ID);
        
        LOG.info("*** Custom Properties ***");
        KafkaPropertiesUtil.printProperties(kafkaConsumerProperties, LOG);

        // Create Kafka consumer
        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(kafkaConsumerProperties);
        
        // Define topic
        String topic = DemoConstant.TOPIC;
        
        // Define partitions
        TopicPartition partitions[] = {
                new TopicPartition(topic, 2),
                new TopicPartition(topic,4),
                new TopicPartition(topic,6)
        };
        
        // Subscribe partitions
        LOG.info("Preparing to subscribe {} partitions", Arrays.asList(partitions));
        kafkaConsumer.assign(Arrays.asList(partitions));
        
//        Set<TopicPartition> partitionsSet = new HashSet<>();
//        partitionsSet.add(new TopicPartition(topic, 2));
//        partitionsSet.add(new TopicPartition(topic, 4));
//        partitionsSet.add(new TopicPartition(topic, 6));
//        
//        LOG.info("Preparing to subscribe {} partitions", partitionsSet);
//        kafkaConsumer.assign(partitionsSet);
        
        LOG.info("Preparing {} topics", kafkaConsumer.listTopics().entrySet());
        for(Map.Entry<String, List<PartitionInfo>> entry : kafkaConsumer.listTopics().entrySet()){
        	LOG.info(" - Topic [{}]", entry.getKey());
        	
            for(PartitionInfo partition : entry.getValue()) {
                Set<Integer> replicas = new HashSet<>();
                Set<Integer> inSync = new HashSet<>();

                for(Node node : partition.replicas()) { 
                	replicas.add(node.id());
                }
                for(Node node : partition.inSyncReplicas()) {
                	inSync.add(node.id());
                }

                LOG.info(" - Partition: {}  Leader: {}  Replicas: {}  InSync: {}",
                        partition.partition(), partition.leader().id(), replicas, inSync);
            }
        }
        
        // Prepare send execution time
        long startTime = System.currentTimeMillis();
        
        LOG.info("Preparing to receive menssages");
        try {
        	
	        while(true){
	        	// Create consumer records
	            ConsumerRecords<String, String> consumerRecords = kafkaConsumer.poll(Duration.ofMillis(2000));
	            LOG.info(GlobalKafkaTemplateConstant.TEMPLATE_LOG_CONSUMER_RECORDS, consumerRecords.count(), consumerRecords.partitions().size());
	
	            // Show Consumer Record info
	            for (ConsumerRecord<String, String> record : consumerRecords){          	
	            	LOG.info(GlobalKafkaTemplateConstant.TEMPLATE_LOG_CONSUMER_RECORD , 
	                        record.key(), record.value(), record.topic(), record.partition(), record.offset(), record.timestamp());
	            }
	            
	            // Define send execution time
	            long elapsedTime = System.currentTimeMillis() - startTime;
	            LOG.info("\t * elapsedTime='{}' seconds ", (elapsedTime / 1000));
	            
	            TimeUnit.SECONDS.sleep(DemoConstant.NUM_SECONDS_DELAY_MESSAGE);
	           
	        }
        }
        finally {
        	// Close consumer
        	kafkaConsumer.close();
        }
        
    }
    

}