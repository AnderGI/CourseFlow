package com.tdd.api.application.save.command;

import com.tdd.api.application.save.CourseSaver;
import com.tdd.api.domain.command.CommandHandler;
import com.tdd.api.domain.course.Course;
import com.tdd.api.domain.course.CourseRepository;
import com.tdd.api.domain.exception.InvalidArgumentException;

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
