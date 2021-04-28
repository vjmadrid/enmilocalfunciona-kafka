package com.acme.architecture.event.driven.exception;

public class AcmeEventDrivenException extends Exception {

	private static final long serialVersionUID = -4879199154956614402L;

	public AcmeEventDrivenException(String message) {
		super(message);
	}

	public AcmeEventDrivenException(Throwable cause) {
		super(cause);
	}

	public AcmeEventDrivenException(String message, Throwable cause) {
		super(message, cause);
	}

}
