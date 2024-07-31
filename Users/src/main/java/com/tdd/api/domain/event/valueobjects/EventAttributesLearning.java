package com.tdd.api.domain.event.valueobjects;

public final class EventAttributesLearning {
	private EventAttributesLearningEng eng;
	private EventAttributesLearningEsp esp;
	
	public EventAttributesLearning(EventAttributesLearningEng eng, EventAttributesLearningEsp esp) {
		this.esp= esp;
		this.eng = eng;
	}
	
	public static EventAttributesLearning create()
	{
		return new EventAttributesLearning(null, null);
	}
	
	public EventAttributesLearning withEnglishLearning(String value) {
		this.eng = new EventAttributesLearningEng(value);
		return this;
	}
	
	public EventAttributesLearning withSpanishLearning(String value)
	{
		this.esp = new EventAttributesLearningEsp(value);
		return this;
	}
}
