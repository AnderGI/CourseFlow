package com.tdd.api.application.find;

import java.util.HashMap;
import java.util.Map;

import org.yaml.snakeyaml.constructor.Constructor;

import com.tdd.api.domain.DomainEntity;
import com.tdd.api.domain.events.DomainEntityHandler;
import com.tdd.api.domain.events.DomainEvent;
import com.tdd.api.domain.events.course.EventBus;

public final class CourseEventBusSync implements EventBus{

	private Map<Class<? extends DomainEntity>, DomainEntityHandler> entityToConverterMapper = new HashMap<>();
	@Override
	public <R extends DomainEntity, H extends DomainEntityHandler> void register(Class<R> entity, H converter) {
		// TODO Auto-generated method stub
		this.entityToConverterMapper.put(entity, converter);
	}
	@Override
	public <R extends DomainEntity, H extends DomainEvent> H dispatch(R entity) {
		// TODO Auto-generated method stub
		DomainEntityHandler handler = entityToConverterMapper.get(entity.getClass());
		return (H) handler.handle(entity);
	}

}
