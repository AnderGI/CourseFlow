package com.tdd.api.infrastructure.controllers.web.post;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tdd.api.application.convert.exception.ExceptionToJsonNodeFactory;
import com.tdd.api.application.save.CreateUserCommand;
import com.tdd.api.application.save.CreateUserCommandHandler;
import com.tdd.api.application.save.UserCommandBusSync;
import com.tdd.api.application.save.UserSaver;
import com.tdd.api.domain.user.UserRepository;

@RestController
@RequestMapping("/users")
public final class PostUserWebController {
	private final ObjectMapper mapper;
	private final UserRepository repo;
	private final UserSaver saver;

	@Autowired
	public PostUserWebController(UserRepository repo, UserSaver userSaver, ObjectMapper mapper) {
		this.repo = repo;
		this.saver = userSaver;
		this.mapper = mapper;
	}

	@PostMapping(path = "", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JsonNode> addUser(@RequestParam MultiValueMap<String, String> userFormData) {
		String email = userFormData.getFirst("email");
		String id = userFormData.getFirst("id");
		// Create Command
		CreateUserCommand command = new CreateUserCommand(email, id);
		// Create Handler
		CreateUserCommandHandler handler = new CreateUserCommandHandler(saver);
		// Create Bus
		UserCommandBusSync bus = new UserCommandBusSync();
		// Register command ad handler
		bus.register(command.getClass(), handler);
		// dispatch method from bus
		try {
			bus.dispatch(command);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return ResponseEntity.unprocessableEntity().body(ExceptionToJsonNodeFactory.parse(e));
		}
		return ResponseEntity.status(HttpStatusCode.valueOf(201)).body(this.givenASuccesfulPostJsonMessage());
	}

	// Temporary solution
	private JsonNode givenASuccesfulPostJsonMessage() {
		ObjectNode successNode = mapper.createObjectNode();
		successNode.put("message", "New user created");
		return successNode;
	}
}
