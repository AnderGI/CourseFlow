package com.tdd.api.application.converters.events;

import com.tdd.api.domain.events.DomainEvent;

public interface EventToResponseConverter<H extends DomainEvent, R> {
	R convert(H event);
}
