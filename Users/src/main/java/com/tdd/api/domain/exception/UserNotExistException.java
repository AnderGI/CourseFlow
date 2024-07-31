package com.tdd.api.domain.exception;

import com.tdd.api.domain.user.UserId;

public final class UserNotExistException extends Exception{
	public UserNotExistException(UserId id)
	{
		super("User with id " + id.getValue() + " does not exist.");
	}
}
