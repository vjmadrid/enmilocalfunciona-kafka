package com.acme.kafka.producer.metrics;

import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.apache.kafka.common.Metric;
import org.apache.kafka.common.MetricName;

public class KafkaMetricsUtil {
	
	private KafkaMetricsUtil() {
		throw new IllegalStateException(this.getClass().getName());
	}

	public static void showMetrics(Map<MetricName, ? extends Metric> kafkaMetrics, Set<String> kafkaMetricsNameFilter) {
		Objects.requireNonNull(kafkaMetrics);
		
		if (!kafkaMetrics.isEmpty()) {
			 Map<String, CustomKafkaMetric> kafkaMetricsMap = kafkaMetrics.entrySet().stream()
		                
    		//Filter out metrics not in metricsNameFilter
            .filter(kafkaMetricName ->
            	kafkaMetricsNameFilter.contains(kafkaMetricName.getKey().name())
            )
            
            //Filter out metrics not in metricsNameFilter
            .filter(metricNameEntry ->
                    !Double.isInfinite(metricNameEntry.getValue().value()) &&
                            !Double.isNaN(metricNameEntry.getValue().value()) &&
                            metricNameEntry.getValue().value() != 0
            )
            
            //Convert Map<MetricName, Metric> into TreeMap<String, CustomKafkaMetric>
            .map(entry -> new CustomKafkaMetric(entry.getKey(), entry.getValue()))
            .collect(Collectors.toMap(
            		CustomKafkaMetric::toString, it -> it, (a, b) -> a, TreeMap::new
            ));
			
			//Output metrics
		        final StringBuilder builder = new StringBuilder(255);
		        builder.append("\n---------------------------------------\n");
		        kafkaMetricsMap.entrySet().forEach(entry -> {
		        	CustomKafkaMetric metricPair = entry.getValue();
		            String name = entry.getKey();
		            
		            builder.append(String.format(Locale.US, "%50s%25s\t\t%,-10.2f\t\t%s\n",
		                    name,
		                    metricPair.getMetricName().name(),
		                    metricPair.getMetric().value(),
		                    metricPair.getMetricName().description()
		            ));
		            
		        });
		        builder.append("\n---------------------------------------\n");
			 
		}

    }

}
