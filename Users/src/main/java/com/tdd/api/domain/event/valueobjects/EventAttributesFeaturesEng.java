package com.tdd.api.domain.event.valueobjects;

public final class EventAttributesFeaturesEng {
	@Override
	public String toString() {
		return "EventAttributesFeaturesEng [value=" + value + "]";
	}

	private String value;
	
	public EventAttributesFeaturesEng(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return this.value;
	}
}
