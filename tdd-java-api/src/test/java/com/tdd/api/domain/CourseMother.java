package com.tdd.api.domain;

final public class CourseMother {
	public static Course create() throws InvalidArgumentException {
		return new Course(CourseIdMother.random(), CourseTitleMother.random());
	}
}
