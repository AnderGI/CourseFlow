package com.tdd.api.application.save_course;

import com.tdd.api.domain.Course;
import com.tdd.api.domain.CourseRepository;
import com.tdd.api.domain.command.CommandHandler;
import com.tdd.api.domain.command.CreateCourseCommand;
import com.tdd.api.domain.exceptions.InvalidArgumentException;

public final class CreateNewCourseCommandHandler implements CommandHandler<CreateCourseCommand> {
	private CourseRepository repo = null;
	private CourseSaver saver = null;
	
	public CreateNewCourseCommandHandler(CourseRepository repo, CourseSaver saver) {
		this.repo = repo;
		this.saver = saver;
	}
	
	@Override
	public void handle(CreateCourseCommand command) throws InvalidArgumentException {
		// Get Values from command and create value objects or entity domain
		String courseId = command.getId();
		String courseTitle = command.getTitle();
		Course course = Course.createFromPrimitives(courseId, courseTitle);
		saver.saveCourse(course);
	}

}
