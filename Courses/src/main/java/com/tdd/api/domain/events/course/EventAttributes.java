package com.tdd.api.domain.events.course;

import java.util.HashMap;
import java.util.Map;

public final class EventAttributes {
	private String id;
	private String title;
	
	public static EventAttributes create() {
		return new EventAttributes();
	}
	
	public EventAttributes withCourseId(String id) {
		this.id = id;
		return this;
	}
	
	public EventAttributes withCourseTitle(String title) {
		this.title = title;
		return this;
	}


	@Override
	public String toString() {
		return "EventAttributes [attributes=" + id + ", " + title + "]";
	}
	
	
}
