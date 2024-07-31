package com.tdd.api.domain.event;

public interface EventPublisher {
	void publish(DomainEvent entity);
}
