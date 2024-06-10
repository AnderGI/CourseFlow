package com.tdd.api.application.convert.event_to_json;

import com.tdd.api.domain.events.DomainEvent;

public interface EventToResponseConverter<H extends DomainEvent, R> {
	R convert(H event);
}
