package com.tdd.api.application.convert;

import java.util.List;

import com.tdd.api.domain.user.DomainEntity;

public interface DomainEntityConverter<R, E extends DomainEntity> {
	R convertSingle(E entity);
	R convertAll(List<E> entities);
}
