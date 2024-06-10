package com.tdd.api.domain.exceptions;

import com.tdd.api.domain.CourseId;

public final class CourseNotExistError extends Exception {
	public CourseNotExistError(CourseId id) {
		super("Course with specified id " + id.getValue() + " does not exist");
	}
}
