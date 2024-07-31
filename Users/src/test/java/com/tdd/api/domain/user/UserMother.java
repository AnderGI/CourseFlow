package com.tdd.api.domain.user;

public final class UserMother {
	public static User create() throws Exception 
	{
		return User.create(UserEmailMother.random(), UserIdMother.random());
	}
}
