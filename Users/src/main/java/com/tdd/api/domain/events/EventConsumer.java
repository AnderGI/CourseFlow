package com.tdd.api.domain.events;

public interface EventConsumer {
	void consume(String event);
}
