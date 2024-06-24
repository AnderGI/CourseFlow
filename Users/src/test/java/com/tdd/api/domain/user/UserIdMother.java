package com.tdd.api.domain.user;

import java.util.UUID;

import com.tdd.api.domain.exception.InvalidArgumentException;

public final class UserIdMother {
	public static UserId random() throws InvalidArgumentException {
		return UserId.createFromPrimitives(UUID.randomUUID().toString());
	}
	
	public static UserId fromPrimitives(String uuid) throws InvalidArgumentException{
		return UserId.createFromPrimitives(uuid);
	}
}
