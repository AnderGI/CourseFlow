package com.tdd.api.domain.command;


public interface CommandBus {
	<C extends Command, H extends CommandHandler> void register(Class<C> command, H handler);
	<C extends Command> void dispatch(C command) throws Exception;
}
