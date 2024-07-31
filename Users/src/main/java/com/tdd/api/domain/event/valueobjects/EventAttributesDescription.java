package com.tdd.api.domain.event.valueobjects;

public final class EventAttributesDescription {
	private EventAttributesDescriptionEng eng;
	private EventAttributesDescriptionEsp esp;
	
	public EventAttributesDescription(EventAttributesDescriptionEng eng, EventAttributesDescriptionEsp esp) {
		this.esp= esp;
		this.eng = eng;
	}
	
	public static EventAttributesDescription create()
	{
		return new EventAttributesDescription(null, null);
	}
	
	public EventAttributesDescription withEnglishDesc(String value) {
		this.eng = new EventAttributesDescriptionEng(value);
		return this;
	}
	
	public EventAttributesDescription withSpanishDesc(String value)
	{
		this.esp = new EventAttributesDescriptionEsp(value);
		return this;
	}

	@Override
	public String toString() {
		return "EventAttributesDescription [eng=" + eng + ", esp=" + esp + "]";
	}

}
