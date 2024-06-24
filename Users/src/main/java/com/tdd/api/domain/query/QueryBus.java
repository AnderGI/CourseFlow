package com.tdd.api.domain.query;

public interface QueryBus {
	<Q extends Query, H extends QueryHandler> void register(Class<Q> queryClass, H handler);
	<R, Q extends Query> R ask(Q query) throws Exception;
}
