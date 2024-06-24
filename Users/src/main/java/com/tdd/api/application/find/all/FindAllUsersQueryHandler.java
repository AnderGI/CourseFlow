package com.tdd.api.application.find.all;

import com.fasterxml.jackson.databind.JsonNode;
import com.tdd.api.application.convert.DomainEntityConverter;
import com.tdd.api.application.convert.json.EntityToJsonConverter;
import com.tdd.api.application.find.UserFinder;
import com.tdd.api.domain.query.QueryHandler;
import com.tdd.api.domain.user.UserRepository;

import jakarta.ws.rs.client.Entity;

public class FindAllUsersQueryHandler implements QueryHandler<FindAllUsersQuery, JsonNode> {

	private final UserFinder finder;
	private final DomainEntityConverter converter;
	public FindAllUsersQueryHandler(UserFinder finder, DomainEntityConverter converter) {
		this.finder = finder;
		this.converter = converter;
	}
	
	@Override
	public JsonNode handle(FindAllUsersQuery query) {
		// Build domain entity
		// Ask UsersFinder service
		return (JsonNode) converter.convertAll(finder.findAll());
	}

}
