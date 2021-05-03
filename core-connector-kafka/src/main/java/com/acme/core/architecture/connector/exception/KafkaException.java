package com.acme.core.architecture.connector.exception;

public class KafkaException extends Exception {

	private static final long serialVersionUID = 2836297900944074449L;

	public KafkaException(String message, Throwable cause) {
		super(message, cause);
	}
}
