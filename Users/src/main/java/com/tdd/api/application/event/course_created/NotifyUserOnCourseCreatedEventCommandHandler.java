package com.tdd.api.application.event.course_created;

import com.tdd.api.domain.event.command.EventCommand;
import com.tdd.api.domain.event.command.EventCommandHandler;

public class NotifyUserOnCourseCreatedEventCommandHandler implements EventCommandHandler {

	@Override
	public <E extends EventCommand> void handle(E eventCommand) {
		// TODO Auto-generated method stub
		// Get the data from the command and call a service that sends 
		System.out.println(eventCommand);
	}

}
