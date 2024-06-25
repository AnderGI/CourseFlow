package com.tdd.api.application.convert;

import java.util.List;

import com.tdd.api.domain.DomainEvent;

public interface DomainEntityConverter<R, E extends DomainEvent> {
	R convertSingle(E entity);
	R convertAll(List<E> entities);
}
