package com.tdd.api.domain.exceptions;

import com.tdd.api.domain.CourseId;

final public class CourseNotExistError extends Exception {
	public CourseNotExistError(CourseId id) {
		super("Course with specified id " + id.getValue() + " does not exist");
	}
}
