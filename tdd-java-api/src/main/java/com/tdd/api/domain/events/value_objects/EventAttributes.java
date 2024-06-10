package com.tdd.api.domain.events.value_objects;

import java.util.HashMap;
import java.util.Map;

public final class EventAttributes {
/*
 {
      "id": "aggregate id",
      "some_parameter": "some value"
    } 
*/
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
