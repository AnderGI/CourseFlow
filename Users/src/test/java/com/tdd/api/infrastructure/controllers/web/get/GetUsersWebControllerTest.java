package com.tdd.api.infrastructure.controllers.web.get;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tdd.api.application.convert.json.EntityToJsonConverter;
import com.tdd.api.application.save.UserSaver;
import com.tdd.api.domain.user.User;
import com.tdd.api.domain.user.UserId;
import com.tdd.api.domain.user.UserMother;
import com.tdd.api.domain.user.UserRepository;
import com.tdd.api.infrastructure.database.inmemory.InMemoryUserTestingRepository;

@WebMvcTest(controllers = GetUserWebController.class)
//Randomize JUNIT tests
//Each test suite will be random among different JVM
//But the randomization will be the same within each JVM once executed
@TestMethodOrder(MethodOrderer.Random.class)
@ActiveProfiles("testing")
public class GetUsersWebControllerTest {
	@Autowired
	private ObjectMapper mapper;
	@MockBean
	private UserRepository repository;
	@Autowired
	private MockMvc mockMvc;

	@BeforeEach
	@Test
	void user_repository_should_be_correctly_injected() 
	{
		assertEquals(Mockito.mock(UserRepository.class).getClass(), repository.getClass());
	}
	
	@Test
	void get_all_users_json_response_when_repository_empty() throws Exception
	{
		mockMvc.perform(
				get("/users")
		)
		.andExpectAll(
				status().isOk(),
				content().contentType(MediaType.APPLICATION_JSON),
				content().json(givenAnEmptyUserRepositoryJson().toString())
		)
		;
	}
	
	@Test
	void get_all_users_json_response_when_repository_not_empty() throws Exception
	{
		
		UserSaver saver = new UserSaver(repository);
		List<User> users = Arrays.asList(UserMother.create(), UserMother.create(), UserMother.create());
		users.forEach(u -> saver.save(u));
		when(repository.getAll()).thenReturn(Optional.of(users));
		mockMvc.perform(
				get("/users")
		)
		.andExpectAll(
				status().isOk(),
				content().contentType(MediaType.APPLICATION_JSON),
				content().json(
						(new EntityToJsonConverter(mapper)).convertAll(users).toString()		
				)
		)
		;
		users.forEach(u -> Mockito.verify(repository).save(u));
	}
	
	@Test
	void get_existing_user_by_id() throws Exception
	{
		
		
		User user = UserMother.create();
		UserSaver saver = new UserSaver(repository);
		saver.save(user);
		when(repository.search(UserId.createFromPrimitives(user.getIdValue())))
		.thenReturn(Optional.of(user));
		mockMvc.perform(
				get("/users/{id}", user.getIdValue())
		)
		.andDo(print())
		.andExpectAll(
				status().isOk(),
				content().contentType(MediaType.APPLICATION_JSON),
				content().json(
						(new EntityToJsonConverter(mapper)).convertSingle(user).toString()		
				)
		)
		;
		Mockito.verify(repository).save(user);
	}
	
	private JsonNode givenAnEmptyUserRepositoryJson() {
		UserRepository repository = this.givenAnInmemorUserTestingRepository();
		((InMemoryUserTestingRepository) repository).clear();
		return (new EntityToJsonConverter(mapper)).convertAll(repository.getAll().orElse(null));
	}

	private InMemoryUserTestingRepository givenAnInmemorUserTestingRepository() {
		return Mockito.mock(InMemoryUserTestingRepository.class);
	}

}
