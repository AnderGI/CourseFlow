package com.tdd.api.domain.user;

import java.util.regex.Pattern;

import com.tdd.api.domain.exception.InvalidArgumentException;

public final class UserEmail {
	private String value;
	
	private UserEmail(String email) throws InvalidArgumentException 
	{
		this.ensureValidEmail(email);
		this.value = email;
	}
	
	private void ensureValidEmail(String email) throws InvalidArgumentException
	{
		boolean isEmailBlackOrEmpty = email.isBlank() || email.isEmpty();
		boolean hasEmailGoodFormat = this.isEmailPatternOk(email);
		if (isEmailBlackOrEmpty || !hasEmailGoodFormat) 
		{
			throw new InvalidArgumentException("Invalid user email format");
		}
	}
	
	private boolean isEmailPatternOk(String value) 
	{
		Pattern EMAIL = Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");
		return EMAIL.matcher(value).matches();
	}
	
	public static UserEmail createFromPrimitives(String value) throws InvalidArgumentException
	{
		return new UserEmail(value);
	}
	
	public String getValue() 
	{
		return this.value;
	}
}
