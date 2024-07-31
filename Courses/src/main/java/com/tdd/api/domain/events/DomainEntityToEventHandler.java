package com.tdd.api.domain.events;

import com.tdd.api.domain.DomainEntity;

public interface DomainEntityToEventHandler<R extends DomainEvent, E extends DomainEntity> {
	R handle(E entity);
}
