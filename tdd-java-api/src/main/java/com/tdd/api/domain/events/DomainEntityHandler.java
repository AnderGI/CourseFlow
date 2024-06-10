package com.tdd.api.domain.events;

import com.tdd.api.domain.DomainEntity;

public interface DomainEntityHandler<R extends DomainEvent, E extends DomainEntity>{
	R handle(E entity);
}
