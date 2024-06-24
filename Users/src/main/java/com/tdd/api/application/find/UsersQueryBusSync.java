package com.tdd.api.application.find;

import java.util.HashMap;
import java.util.Map;

import com.tdd.api.domain.query.Query;
import com.tdd.api.domain.query.QueryBus;
import com.tdd.api.domain.query.QueryHandler;

public class UsersQueryBusSync implements QueryBus {

	private static Map<Class<? extends Query>, QueryHandler> queryToHanlderMap = new HashMap<>();
	
	@Override
	public <Q extends Query, H extends QueryHandler> void register(Class<Q> queryClass, H handler) {
		queryToHanlderMap.put(queryClass, handler);
	}

	@Override
	public <R, Q extends Query> R ask(Q query) throws Exception {
		QueryHandler handler = queryToHanlderMap.get(query.getClass());
		// Validate null handler
		return (R) handler.handle(query);
	}

}
