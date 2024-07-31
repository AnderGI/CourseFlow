package com.tdd.api.application.save;

import com.tdd.api.domain.command.CommandHandler;
import com.tdd.api.domain.user.User;
import com.tdd.api.domain.user.UserEmail;
import com.tdd.api.domain.user.UserId;

public final class CreateUserCommandHandler implements CommandHandler<CreateUserCommand>{

	private final UserSaver saver;
	
	public CreateUserCommandHandler(UserSaver saver)
	{
		this.saver = saver;
	}
	
	@Override
	public void handle(CreateUserCommand command) throws Exception {
		// Create Value Objects
		UserEmail email = UserEmail.createFromPrimitives(command.getEmail());
		UserId id = UserId.createFromPrimitives(command.getId());
		// Create Entity
		User user = User.create(email, id);
		// Call Saver
		saver.save(user);
	}

}
