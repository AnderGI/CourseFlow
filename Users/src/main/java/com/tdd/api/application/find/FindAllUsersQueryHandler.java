package com.tdd.api.application.find;

import com.fasterxml.jackson.databind.JsonNode;
import com.tdd.api.application.convert.DomainEntityConverter;
import com.tdd.api.domain.query.QueryHandler;

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
