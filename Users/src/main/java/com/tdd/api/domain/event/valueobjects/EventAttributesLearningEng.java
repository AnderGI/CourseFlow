package com.tdd.api.domain.event.valueobjects;

public final class EventAttributesLearningEng {
	@Override
	public String toString() {
		return "EventAttributesLearningEng [value=" + value + "]";
	}

	private String value;
	
	public EventAttributesLearningEng(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return this.value;
	}
}
