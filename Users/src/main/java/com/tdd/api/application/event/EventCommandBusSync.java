package com.tdd.api.application.event;

import java.util.HashMap;
import java.util.Map;

import com.tdd.api.domain.event.command.EventCommand;
import com.tdd.api.domain.event.command.EventCommandBus;
import com.tdd.api.domain.event.command.EventCommandHandler;

public class EventCommandBusSync implements EventCommandBus {
	private Map<Class<? extends EventCommand>, EventCommandHandler> eventCommandToHandlerMap = 
			new HashMap<>();
	@Override
	public <E extends EventCommand, H extends EventCommandHandler> void register(Class<E> eventCommand, H handler) {
		eventCommandToHandlerMap.put(eventCommand, handler);
		
	}

	@Override
	public <E extends EventCommand> void dispatch(E eventCommand) {
		EventCommandHandler handler = eventCommandToHandlerMap.get(eventCommand.getClass());
		// Validate null handler
		handler.handle(eventCommand);
		
	}

}
