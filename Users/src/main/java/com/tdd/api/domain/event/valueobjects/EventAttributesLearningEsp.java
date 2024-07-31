package com.tdd.api.domain.event.valueobjects;

public final class EventAttributesLearningEsp {
	@Override
	public String toString() {
		return "EventAttributesLearningEsp [value=" + value + "]";
	}

	private String value;
	
	public EventAttributesLearningEsp(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return this.value;
	}
}
