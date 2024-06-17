package com.tdd.api.domain;

import com.tdd.api.domain.course.Course;
import com.tdd.api.domain.exception.InvalidArgumentException;

final public class CourseMother {
	public static Course create() throws InvalidArgumentException {
		return new Course(CourseIdMother.random(), CourseTitleMother.random());
	}
}
