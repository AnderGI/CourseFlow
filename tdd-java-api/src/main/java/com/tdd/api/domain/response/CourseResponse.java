package com.tdd.api.domain.response;

final public class CourseResponse implements Response{
	private String id;
	private String title;
	
	public CourseResponse(String id, String title) {
		this.id = id;
		this.title = title;
	}
	
	public String getId() {
		return this.id;
	}

	public String getTitle() {
		return this.title;
	}

}
