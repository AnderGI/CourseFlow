package com.tdd.api.domain.exceptions;

final public class InvalidArgumentException extends Exception {
	public InvalidArgumentException(String message) {
		super(message);
	}
}
