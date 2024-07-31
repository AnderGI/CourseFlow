package com.tdd.api.infrastructure.controllers.web.get;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.tdd.api.application.convert.DomainEntityConverter;
import com.tdd.api.application.exception_converter.ExceptionToJsonNodeFactory;
import com.tdd.api.application.find.FindAllUsersQuery;
import com.tdd.api.application.find.FindAllUsersQueryHandler;
import com.tdd.api.application.find.FindUserByIdQuery;
import com.tdd.api.application.find.FindUserByIdQueryHandler;
import com.tdd.api.application.find.UserFinder;
import com.tdd.api.application.find.UsersQueryBusSync;
import com.tdd.api.domain.user.UserRepository;

@RestController
@RequestMapping("/users")
public final class GetUserWebController {
	
	private final UserRepository repository;
	private final UserFinder finder;
	private final DomainEntityConverter converter;
	@Autowired
	public GetUserWebController(UserRepository repository, UserFinder finder,
			DomainEntityConverter converter) {
		this.repository = repository;
		this.finder = finder;
		this.converter = converter;
	}
	
	@GetMapping(path = "")
	public ResponseEntity<JsonNode> getAllUsers()
	{
		// Create Query
		FindAllUsersQuery queryAll = new FindAllUsersQuery();
		// Create corresponding handler
		FindAllUsersQueryHandler allHandler = new FindAllUsersQueryHandler(finder, converter);
		// Register in query bus sync with its corresponding handler
		UsersQueryBusSync bus = new UsersQueryBusSync();
		bus.register(queryAll.getClass(), allHandler);
		// Call ask method in query bus
		try {
			return ResponseEntity.ok(bus.ask(queryAll));
		} catch (Exception e) {
			return ResponseEntity.status(404).body(ExceptionToJsonNodeFactory.parse(e));
		}
	}
	
	@GetMapping(path = "/{id}",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JsonNode> getUserById(@PathVariable String id){
		FindUserByIdQuery query = new FindUserByIdQuery(id);
		FindUserByIdQueryHandler handler = new FindUserByIdQueryHandler(finder, converter);
		UsersQueryBusSync bus = new UsersQueryBusSync();
		bus.register(query.getClass(), handler);
		try {
			return ResponseEntity.ok(bus.ask(query));
		}catch(Exception exp) {
			return ResponseEntity.status(404).body(ExceptionToJsonNodeFactory.parse(exp));
		}
	}
}
