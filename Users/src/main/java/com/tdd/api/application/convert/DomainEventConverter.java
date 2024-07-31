package com.tdd.api.application.convert;

import java.util.List;

import com.tdd.api.domain.event.DomainEvent;
import com.tdd.api.domain.user.DomainEntity;

public interface DomainEventConverter<R, E extends DomainEvent> {
	R convertSingle(E event);
	R convertAll(List<E> events);
}
