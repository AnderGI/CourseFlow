package com.tdd.api.domain.events.value_objects;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.springframework.util.MultiValueMap;

import com.tdd.api.domain.Course;

public final class EventData {

	/*
"id": "event id",
    "type": "domain_event_name", codelytv.video.1.event.video.published 
    "occurred_on": "date event has occurred on", 
    "attributes": {
      "id": "aggregate id",
      "some_parameter": "some value"
    },
    "meta": {
      "some_key": "some value",
      "host": "machine hostname"
    } 
*/
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

}
