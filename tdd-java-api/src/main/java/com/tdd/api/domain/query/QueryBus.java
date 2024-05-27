package com.tdd.api.domain.query;

public interface QueryBus {
	<R> R ask(Query query) throws Exception; 
	 <Q extends Query, R> void registerHandler(Class<Q> queryType, QueryHandler<Q, R> handler);
}
