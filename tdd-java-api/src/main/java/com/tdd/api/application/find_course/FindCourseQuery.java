package com.tdd.api.application.find_course;

import com.tdd.api.domain.Query;

final public class FindCourseQuery implements Query {
	private String uuid;
	
	public FindCourseQuery(String id) {
		this.uuid = id;
	}
	
	public String getCourseQueryId() {
		return this.uuid;
	}
}
