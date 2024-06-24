package com.tdd.api.application.save;


import java.util.HashMap;
import java.util.Map;

import com.tdd.api.domain.command.Command;
import com.tdd.api.domain.command.CommandBus;
import com.tdd.api.domain.command.CommandHandler;

public final class UserCommandBusSync implements CommandBus{

	private Map<Class<? extends Command>, CommandHandler> commandToHanlderMap = new HashMap<>();
	
	@Override
	public <C extends Command, H extends CommandHandler> void register(Class<C> command, H handler) {
		commandToHanlderMap.put(command, handler);
		
	}

	@Override
	public <C extends Command> void dispatch(C command) throws Exception {
		CommandHandler handler = commandToHanlderMap.get(command.getClass());
		// Validate null handler
		handler.handle(command);
		
	}

}
