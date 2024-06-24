package com.tdd.api.application.find.single;

import com.fasterxml.jackson.databind.JsonNode;
import com.tdd.api.application.convert.DomainEntityConverter;
import com.tdd.api.application.find.UserFinder;
import com.tdd.api.domain.query.QueryHandler;
import com.tdd.api.domain.user.User;
import com.tdd.api.domain.user.UserId;

public class FindUserByIdQueryHandler implements QueryHandler<FindUserByIdQuery, JsonNode> {
	
	private final UserFinder finder;
	private final DomainEntityConverter converter;
	public FindUserByIdQueryHandler(UserFinder finder, DomainEntityConverter converter) {
		this.finder = finder;
		this.converter = converter;
	}
	
	@Override
	public JsonNode handle(FindUserByIdQuery query) throws Exception {
		UserId id = UserId.createFromPrimitives(query.getId());
		User user = finder.find(id);
		return (JsonNode) converter.convertSingle(user);
	}

}
