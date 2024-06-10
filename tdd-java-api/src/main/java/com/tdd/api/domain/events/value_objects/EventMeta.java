package com.tdd.api.domain.events.value_objects;

import java.util.HashMap;
import java.util.Map;

public final class EventMeta {

	private String host;
	
	public static EventMeta create() {
		return new EventMeta();
	}
	
	public EventMeta withHostMetaInfo(String info) {
		this.host = info;
		return this;
	}

	@Override
	public String toString() {
		return "EventMeta [meta=" + host + "]";
	}
	
}
