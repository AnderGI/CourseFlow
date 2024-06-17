package com.tdd.api.domain.exception;

import com.tdd.api.domain.course.CourseId;

public final class CourseNotExistException extends Exception {
	public CourseNotExistException(CourseId id) {
		super("Course with specified id " + id.getValue() + " does not exist");
	}
}
