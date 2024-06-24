package com.tdd.api.domain.user;

import java.util.Objects;
import java.util.UUID;
import java.util.regex.Pattern;

import com.tdd.api.domain.exception.InvalidArgumentException;

public final class UserId {
	
	private String value;
	
	private UserId(String id) throws InvalidArgumentException 
	{
		this.ensureValidId(id);
		this.value = id;
	}
	
	private void ensureValidId(String id) throws InvalidArgumentException
	{
		boolean isIdBlankOrEmpty = id.isBlank() || id.isEmpty();
		boolean isUuid = isUuidPatternOk(id);
		if (isIdBlankOrEmpty || !isUuid) 
		{
			throw new InvalidArgumentException("Invalid user id");
		}
	}
	
	private boolean isUuidPatternOk(String value) 
	{
		Pattern UUID_REGEX = Pattern
				.compile("^[0-9a-f]{8}-[0-9a-f]{4}-[0-5][0-9a-f]{3}-[089ab][0-9a-f]{3}-[0-9a-f]{12}$");
		return UUID_REGEX.matcher(value).matches();
	}
	
	public static UserId createFromPrimitives(String value) throws InvalidArgumentException
	{
		return new UserId(value);
	}
	
	public String getValue() 
	{
		return this.value;
	}
}
