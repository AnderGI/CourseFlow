package com.tdd.api.application.find;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tdd.api.domain.exception.UserNotExistException;
import com.tdd.api.domain.user.User;
import com.tdd.api.domain.user.UserId;
import com.tdd.api.domain.user.UserRepository;

@Service
public final class UserFinder {
	private UserRepository repo;
	@Autowired
	public UserFinder(UserRepository repo)
	{
		this.repo = repo;
	}
	
	public User find(UserId id) throws UserNotExistException
	{
		User u = repo.search(id).orElse(null);
		if (u == null)
		{
			throw new UserNotExistException(id);
		}
		
		return u;
	}
	
	public List<User> findAll() {
		return repo.getAll().orElse(null);
	}
}
