package com.tdd.api.domain.command;

public interface CommandBus {
	<R extends Command, H extends CommandHandler> void register(Class<R> command, H handler);
	<R extends Command> void dispatch(R command) throws Exception;
}
