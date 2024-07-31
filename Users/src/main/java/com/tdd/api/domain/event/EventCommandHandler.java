package com.tdd.api.domain.event;

public interface EventCommandHandler {
	<E extends EventCommand> void handle(E eventCommand);
}
