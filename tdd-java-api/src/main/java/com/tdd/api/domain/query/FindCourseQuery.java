package com.tdd.api.domain.query;

public final class FindCourseQuery implements Query {
	private String uuid;
	
	public FindCourseQuery(String id) {
		this.uuid = id;
	}
	
	public String getCourseQueryId() {
		return this.uuid;
	}
}
