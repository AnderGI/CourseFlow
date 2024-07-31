package com.tdd.api.domain.event;

public interface EventConsumer {
	 <E extends EventCommand> void handle(E Event);
}
