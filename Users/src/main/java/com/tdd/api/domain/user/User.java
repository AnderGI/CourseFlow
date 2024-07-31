package com.tdd.api.domain.user;

import java.util.Objects;


public final class User implements DomainEntity{
	private UserEmail email;
	private UserId id;
	
	private User(String email, String id) throws Exception
	{
		this.email = UserEmail.createFromPrimitives(email);
		this.id = UserId.createFromPrimitives(id);
	}
	
	public static User createFromPrimitives(String email, String id) throws Exception 
	{
		return new User(email, id);
	}
	
	public static User create(UserEmail email, UserId id) throws Exception 
	{
		return new User(email.getValue(), id.getValue());
	}
	
	public String getEmailValue() 
	{
		return this.email.getValue();
	}
	
	public String getIdValue() 
	{
		return this.id.getValue();
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(id, other.id);
	}
	
	
}
