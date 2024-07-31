package com.tdd.api.domain.event.valueobjects;

public final class EventAttributesDescriptionEng {
	@Override
	public String toString() {
		return "EventAttributesDescriptionEng [value=" + value + "]";
	}

	private String value;
	
	public EventAttributesDescriptionEng(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return this.value;
	}
}
