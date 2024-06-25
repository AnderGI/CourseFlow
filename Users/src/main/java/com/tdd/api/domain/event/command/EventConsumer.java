package com.tdd.api.domain.event.command;

public interface EventConsumer {
	 <E extends EventCommand> void handle(E Event);
}
