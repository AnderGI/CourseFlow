package com.tdd.api.domain.events;

import com.tdd.api.domain.DomainEntity;

public interface EventBus {
	<R extends DomainEntity, H extends DomainEntityHandler> void register(Class<R> entity, H converter);
	<R extends DomainEntity, H extends DomainEvent> H dispatch(R entity);
}
