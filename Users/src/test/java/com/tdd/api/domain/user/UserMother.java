package com.tdd.api.domain.user;

import com.tdd.api.domain.exception.InvalidArgumentException;

public final class UserMother {
	public static User create() throws Exception 
	{
		return User.create(UserEmailMother.random(), UserIdMother.random());
	}
}
