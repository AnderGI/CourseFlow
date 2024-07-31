package com.tdd.api.application.find;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.springframework.test.context.ActiveProfiles;

import com.tdd.api.application.save.UserSaver;
import com.tdd.api.domain.exception.InvalidArgumentException;
import com.tdd.api.domain.exception.UserNotExistException;
import com.tdd.api.domain.user.User;
import com.tdd.api.domain.user.UserId;
import com.tdd.api.domain.user.UserIdMother;
import com.tdd.api.domain.user.UserMother;
import com.tdd.api.domain.user.UserRepository;

@TestMethodOrder(MethodOrderer.Random.class)
@ActiveProfiles("testing")
public class UserFinderTest {

	@Test
	void it_should_find_all_users() throws Exception {
		UserRepository repo = this.givenAMockUserRepository();
		UserSaver saver = new UserSaver(repo);
		List<User> users = Arrays.asList(UserMother.create(), UserMother.create(), UserMother.create());
		users.forEach(u -> saver.save(u));
		UserFinder finder = new UserFinder(repo);
		// When
		when(repo.getAll()).thenReturn(Optional.of(users));
		// Then
		assertEquals(finder.findAll(), users);
	}

	@Test
	void it_should_find_existing_user() throws Exception {
		UserRepository repo = this.givenAMockUserRepository();
		UserSaver saver = new UserSaver(repo);
		User user = UserMother.create();
		UserId id = UserIdMother.fromPrimitives(user.getIdValue());
		saver.save(user);
		UserFinder finder = new UserFinder(repo);
		// When
		when(repo.search(id)).thenReturn(Optional.of(user));
		// Then
		assertEquals(finder.find(id), user);

	}

	@Test
	void it_should_not_find_non_existing_user() throws InvalidArgumentException // email, even though, is valid
	{
		// Given
		UserRepository repo = this.givenAMockUserRepository();
		UserId fakeid = UserIdMother.random();
		UserFinder finder = new UserFinder(repo);
		// When repo gets call
		when(repo.search(fakeid)).thenReturn(Optional.empty());
		// Finder trhows exception
		assertThrows(UserNotExistException.class, () -> finder.find(fakeid));

	}

	private UserRepository givenAMockUserRepository() {
		return Mockito.mock(UserRepository.class);
	}

}
