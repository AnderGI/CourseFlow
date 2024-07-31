package com.tdd.api.domain.event;

import com.tdd.api.domain.event.valueobjects.EventData;

public final class CourseEvent implements DomainEvent {
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

	public String getRoutingKey() {
		return this.data.getType();
	}
}
