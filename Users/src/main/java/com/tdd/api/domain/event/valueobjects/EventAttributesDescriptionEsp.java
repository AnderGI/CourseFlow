package com.tdd.api.domain.event.valueobjects;

public final class EventAttributesDescriptionEsp {
	@Override
	public String toString() {
		return "EventAttributesDescriptionEsp [value=" + value + "]";
	}

	private String value;
	
	public EventAttributesDescriptionEsp(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return this.value;
	}
}
