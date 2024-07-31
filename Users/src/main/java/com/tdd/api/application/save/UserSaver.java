package com.tdd.api.application.save;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tdd.api.domain.user.User;
import com.tdd.api.domain.user.UserRepository;

@Service
public final class UserSaver {
	private UserRepository repository;
	@Autowired
	public UserSaver(UserRepository repo)
	{
		this.repository = repo;
	}
	
	public void save(User user)
	{
		repository.save(user);
	}
}
