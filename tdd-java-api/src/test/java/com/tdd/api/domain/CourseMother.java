package com.tdd.api.domain;

import com.tdd.api.domain.exceptions.InvalidArgumentException;

final public class CourseMother {
	public static Course create() throws InvalidArgumentException {
		return new Course(CourseIdMother.random(), CourseTitleMother.random());
	}
}
