package com.tdd.api.domain.event.command;

public interface EventCommandHandler {
	<E extends EventCommand> void handle(E eventCommand);
}
