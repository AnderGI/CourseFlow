package com.tdd.api.application.bus;

import java.util.HashMap;
import java.util.Map;

import com.tdd.api.domain.command.Command;
import com.tdd.api.domain.command.CommandBus;
import com.tdd.api.domain.command.CommandHandler;

final public class CourseCommandBusSync implements CommandBus{

	private Map<Class<? extends Command>, CommandHandler> commandToHandlerMap = new HashMap<>();
	
	@Override
	public <R extends Command, H extends CommandHandler> void register(Class<R> command,H handler) {
		commandToHandlerMap.put(command, handler);
		
	}

	@Override
	public <R extends Command> void dispatch(R command) throws Exception {
		CommandHandler<R> handler = commandToHandlerMap.get(command.getClass());
		// not check for nullable values, at leat for now
		handler.handle(command);
	}

}
