package com.tdd.api.application.save;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.tdd.api.domain.user.User;
import com.tdd.api.domain.user.UserMother;
import com.tdd.api.domain.user.UserRepository;

public class UserSaverTest {
	@Test
	void it_saves_valid_user() throws Exception
	{
		UserRepository repo = this.givenAMockUserRepository();
		UserSaver saver = this.givenAUserSaver(repo);
		User user = UserMother.create();
		assertDoesNotThrow(() -> saver.save(user));
		// Verify calls
		Mockito.verify(repo).save(user);
	}
	
	
	private UserRepository givenAMockUserRepository()
	{
		return Mockito.mock(UserRepository.class);
	}
	
	private UserSaver givenAUserSaver(UserRepository repo)
	{
		return new UserSaver(repo);
	}
}
