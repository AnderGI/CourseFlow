package com.tdd.api.domain.event.valueobjects;

import java.util.HashMap;
import java.util.Map;

public final class EventAttributes {
	private String title;
	private EventAttributesDescription description;
	private EventAttributesLearning learnings;
	private EventAttributesFeatures features; 
	
	public static EventAttributes create() {
		return new EventAttributes();
	}
	
	public EventAttributes withCourseTitle(String title) {
		this.title = title;
		return this;
	}

	public EventAttributes withCourseDescription(EventAttributesDescription desc) {
		this.description = desc;
		return this;
	}

	public EventAttributes withCourseLearnings(EventAttributesLearning learnings) {
		this.learnings = learnings;
		return this;
	}

	public EventAttributes withCourseFeatures(EventAttributesFeatures features) {
		this.features = features;
		return this;
	}
	
	@Override
	public String toString() {
		return "EventAttributes [title=" + title + ", description=" + description + "]";
	}


	
	
}
