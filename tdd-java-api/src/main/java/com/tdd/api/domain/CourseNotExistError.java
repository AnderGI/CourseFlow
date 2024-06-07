package com.tdd.api.domain;

final public class CourseNotExistError extends Exception {
	public CourseNotExistError(CourseId id) {
		super("Course with specified id " + id.getValue() + " does not exist");
	}
}
