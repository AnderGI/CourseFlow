package com.tdd.api.domain.event.valueobjects;

public final class EventAttributesFeaturesEsp {
	@Override
	public String toString() {
		return "EventAttributesFeaturesEsp [value=" + value + "]";
	}

	private String value;
	
	public EventAttributesFeaturesEsp(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return this.value;
	}
}
