package com.tdd.api.domain.event.valueobjects;

public final class EventAttributesFeatures {
	private EventAttributesFeaturesEng eng;
	private EventAttributesFeaturesEsp esp;
	
	public EventAttributesFeatures(EventAttributesFeaturesEng eng, EventAttributesFeaturesEsp esp) {
		this.esp= esp;
		this.eng = eng;
	}
	
	public static EventAttributesFeatures create()
	{
		return new EventAttributesFeatures(null, null);
	}
	
	public EventAttributesFeatures withEnglishFeatures(String value) {
		this.eng = new EventAttributesFeaturesEng(value);
		return this;
	}
	
	public EventAttributesFeatures withSpanishFeatures(String value)
	{
		this.esp = new EventAttributesFeaturesEsp(value);
		return this;
	}

	@Override
	public String toString() {
		return "EventAttributesFeatures [eng=" + eng + ", esp=" + esp + "]";
	}
}
