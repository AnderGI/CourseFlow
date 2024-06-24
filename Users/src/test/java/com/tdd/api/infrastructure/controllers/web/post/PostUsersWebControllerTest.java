package com.tdd.api.infrastructure.controllers.web.post;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tdd.api.application.convert.exception.ExceptionToJsonNodeFactory;
import com.tdd.api.domain.exception.InvalidArgumentException;
import com.tdd.api.domain.user.User;
import com.tdd.api.domain.user.UserMother;
import com.tdd.api.domain.user.UserRepository;

@WebMvcTest(controllers = PostUserWebController.class)
@TestMethodOrder(MethodOrderer.Random.class)
@ActiveProfiles("testing")
public class PostUsersWebControllerTest {
	@Autowired
	private ObjectMapper mapper;
	@MockBean
	private UserRepository repo;
	@Autowired
	private MockMvc mockMvc;
	
	@BeforeEach
	@Test
	void user_repository_should_be_correctly_instantiated()
	{
		assertEquals(repo.getClass(), Mockito.mock(UserRepository.class).getClass());
	}
	
	@Test
	void it_should_save_a_correct_user() throws Exception
	{
		// Given
		User user = UserMother.create();
		JsonNode successNode = this.givenASuccesfulPostJsonMessage();
		// Then
		mockMvc.perform(
				post("/users")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
				.formFields(this.fromUserToFromField(user))
		)
		.andExpectAll(
				status().isCreated(),
				content().contentType(MediaType.APPLICATION_JSON_VALUE),
				content().json(successNode.toString())
		)
		;
	}

	
	
	@Test
	void it_not_should_save_an_incorrect_user() throws Exception
	{
		
		// Then
		mockMvc.perform(
				post("/users")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
				.formFields(new LinkedMultiValueMap<>())
		)
		.andExpectAll(
				status().isUnprocessableEntity(),
				content().contentType(MediaType.APPLICATION_JSON_VALUE),
				content().json(ExceptionToJsonNodeFactory.parse(new InvalidArgumentException()).toString())
		)
		;
	}
	
	private MultiValueMap<String, String> fromUserToFromField(User user) {
		// Manually
		// Better opt -> user reflection and recursiveness
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("email", user.getEmailValue());
		map.add("id", user.getIdValue());
		return map;
	}
	
	private JsonNode givenASuccesfulPostJsonMessage() {
		ObjectNode successNode = mapper.createObjectNode();
		successNode.put("message", "New user created");
		return successNode;
	}
	
}
