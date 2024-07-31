package com.tdd.api.infrastructure.database.inmemory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.tdd.api.domain.user.User;
import com.tdd.api.domain.user.UserId;
import com.tdd.api.domain.user.UserRepository;

public final class InMemoryUserRepository implements UserRepository {

	private static List<User> database = new ArrayList<>();
	
	@Override
	public Optional<List<User>> getAll() {
		return Optional.of(database);
	}

	@Override
	public Optional<User> search(UserId id) {
		return database.stream()
				.filter(u -> u.getIdValue().equalsIgnoreCase(id.getValue()))
				.findFirst();
	}

	@Override
	public void save(User user) {
		database.add(user);
	}

}
