package com.tdd.api.application.find.query.find_one;

import com.tdd.api.domain.query.Query;

public final class FindCourseQuery implements Query {
	private String uuid;
	
	public FindCourseQuery(String id) {
		this.uuid = id;
	}
	
	public String getCourseQueryId() {
		return this.uuid;
	}
}
