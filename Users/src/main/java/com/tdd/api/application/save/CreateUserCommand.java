package com.tdd.api.application.save;

import com.tdd.api.domain.command.Command;

public final class CreateUserCommand implements Command{
	private String email;
	private String id;
	public CreateUserCommand(String email, String id)
	{
		this.email = email;
		this.id = id;
	}
	
	public String getEmail()
	{
		return this.email;
	}
	
	public String getId()
	{
		return this.id;
	}
}
