package com.tdd.api.application.find.single;

import com.tdd.api.domain.query.Query;

public final class FindUserByIdQuery implements Query {
	private String id;
	
	public FindUserByIdQuery(String id) {
		this.id = id;
	}
	
	public String getId() {
		return this.id;
	}
}
