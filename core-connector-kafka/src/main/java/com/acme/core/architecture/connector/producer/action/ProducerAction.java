package com.acme.core.architecture.connector.producer.action;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

public interface ProducerAction<K, V> extends Function<ProducerRecord<K, V>, CompletableFuture<RecordMetadata>>, AutoCloseable {

}
