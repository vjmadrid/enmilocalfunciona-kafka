package com.acme.core.architecture.connector.exception;

public class KafkaRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 7263722544483004388L;

	public KafkaRuntimeException() {
	}

	public KafkaRuntimeException(String message) {
		super(message);
	}

	public KafkaRuntimeException(Throwable throwable) {
		super(throwable);
	}

	public KafkaRuntimeException(String message, Throwable throwable) {
		super(message, throwable);
	}

}
