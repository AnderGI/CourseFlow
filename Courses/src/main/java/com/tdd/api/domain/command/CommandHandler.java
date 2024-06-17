package com.tdd.api.domain.command;

public interface CommandHandler<R extends Command> {
	void handle(R command) throws Exception;
}
