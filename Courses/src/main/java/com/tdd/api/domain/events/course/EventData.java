package com.tdd.api.domain.events.course;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.springframework.util.MultiValueMap;


public final class EventData {
	private String id;
	private String type;
	private String ocurred_on;
	private EventAttributes attributes;
	private EventMeta meta;
	
	public static EventData create() {
		return new EventData();
	}
	
	public EventData withId() {
		this.id = this.randomUUID();
		return this;
	}
	
	public EventData withType(String type) {
		this.type = type;
		return this;
	}
	
	public EventData ocurredOn(Date ocurredOn) {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		this.ocurred_on = format.format(ocurredOn);
		return this;
	}
	
	public EventData withAttributes(EventAttributes attributes) {
		this.attributes = attributes;
		// attributes to course ¿?
		return this;
	}
	
	public EventData withMeta(EventMeta metaInfo) {
		this.meta = metaInfo;
		return this;
	}
	
	private String randomUUID() {
		return UUID.randomUUID().toString();
	}
	
	@Override
	public String toString() {
		return "EventData [id=" + id + ", type=" + type + ", ocurred_on=" + ocurred_on + ", attributes=" + attributes
				+ ", meta=" + meta + "]";
	}

	public String getType() {
		return this.type;
	}
}
