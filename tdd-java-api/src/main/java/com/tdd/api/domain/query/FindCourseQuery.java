package com.tdd.api.domain.query;

final public class FindCourseQuery implements Query {
	private String uuid;
	
	public FindCourseQuery(String id) {
		this.uuid = id;
	}
	
	public String getCourseQueryId() {
		return this.uuid;
	}
}
