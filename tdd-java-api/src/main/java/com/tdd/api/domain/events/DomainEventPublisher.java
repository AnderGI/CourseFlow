package com.tdd.api.domain.events;

import com.tdd.api.domain.DomainEntity;

public interface DomainEventPublisher {
	void publish(DomainEntity entity);
}
