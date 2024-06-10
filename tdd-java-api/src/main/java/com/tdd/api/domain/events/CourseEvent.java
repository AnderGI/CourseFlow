package com.tdd.api.domain.events;

import com.tdd.api.domain.events.value_objects.EventData;

public final class CourseEvent implements DomainEvent{
	private EventData data;
	
	public static CourseEvent create() {
		return new CourseEvent();
	}
	public CourseEvent withEventData(EventData data) {
		this.data = data;
		return this;
	}
	@Override
	public String toString() {
		return this.data.toString();
	}

}
