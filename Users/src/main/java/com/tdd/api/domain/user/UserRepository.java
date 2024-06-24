package com.tdd.api.domain.user;

import java.util.List;
import java.util.Optional;


public interface UserRepository {
	Optional<List<User>> getAll();
	Optional<User> search(UserId id);
	void save(User user);
}
