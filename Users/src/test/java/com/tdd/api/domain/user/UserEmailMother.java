package com.tdd.api.domain.user;

import com.github.javafaker.Faker;
import com.tdd.api.domain.exception.InvalidArgumentException;

public final class UserEmailMother {
	public static UserEmail random() throws InvalidArgumentException {
		Faker faker = new Faker();
		String fakeEmail = faker.internet().emailAddress();
		return UserEmail.createFromPrimitives(fakeEmail);
	}
	
	public static UserEmail fromPrimitive(String email) throws InvalidArgumentException {
		return UserEmail.createFromPrimitives(email);
	}
}
