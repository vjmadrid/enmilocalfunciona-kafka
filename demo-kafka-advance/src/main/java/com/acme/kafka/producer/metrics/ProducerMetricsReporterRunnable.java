package com.acme.kafka.producer.metrics;

import java.util.Map;
import java.util.Set;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.common.Metric;
import org.apache.kafka.common.MetricName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProducerMetricsReporterRunnable implements Runnable {
	
	private static final Logger LOG = LoggerFactory.getLogger(ProducerMetricsReporterRunnable.class);
	
	private final KafkaProducer<String, String> kafkaProducer;
	
	private Set<String> kafkaMetricsNameFilter;
	
	public ProducerMetricsReporterRunnable(final KafkaProducer<String, String> kafkaProducer) {
		LOG.info("[ProducerMetricsReporterRunnable] *** Init ***");
		
		// Create Kafka producer
        this.kafkaProducer = kafkaProducer;
        
     // Create Kafka metrics
        this.kafkaMetricsNameFilter = KafkaMetricsConfig.generateKafkaMetricsNameFilterSet();
    }

	@Override
	public void run() {
		LOG.info("[ProducerMetricsReporterRunnable] *** Run ***");
		
		while (true) {
            final Map<MetricName, ? extends Metric> kafkaMetrics = kafkaProducer.metrics();

            KafkaMetricsUtil.showMetrics(kafkaMetrics, kafkaMetricsNameFilter);
            
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
            	LOG.warn("metrics interrupted");
                Thread.interrupted();
                break;
            }
        }
		
	}

}
