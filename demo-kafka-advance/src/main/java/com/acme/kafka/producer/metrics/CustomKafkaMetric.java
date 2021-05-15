package com.acme.kafka.producer.metrics;

import org.apache.kafka.common.Metric;
import org.apache.kafka.common.MetricName;

import lombok.Data;

@Data
public class CustomKafkaMetric {
	
	private MetricName metricName;
    private Metric metric;

    public CustomKafkaMetric(MetricName metricName, Metric metric) {
        this.metricName = metricName;
        this.metric = metric;
    }

}
