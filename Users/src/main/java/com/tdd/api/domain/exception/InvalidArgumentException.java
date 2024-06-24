package com.tdd.api.domain.exception;

public final class InvalidArgumentException extends Exception{
	public InvalidArgumentException(String message) {
		super(message);
	}
	public InvalidArgumentException() {
		super();
	}
}
