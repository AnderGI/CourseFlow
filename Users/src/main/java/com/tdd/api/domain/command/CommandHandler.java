package com.tdd.api.domain.command;

public interface CommandHandler<C extends Command> {
	void handle(C command) throws Exception;
}
