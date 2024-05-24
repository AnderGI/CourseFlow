package com.tdd.api.domain;

public interface QueryHandler<Q extends Query, R> {
	R handle(Q query) throws Exception;
}
