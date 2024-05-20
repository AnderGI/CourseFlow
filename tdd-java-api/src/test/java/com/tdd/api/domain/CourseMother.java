package com.tdd.api.domain;

import com.tdd.api.domain.Course;
import com.tdd.api.domain.InvalidArgumentException;

final public class CourseMother {
	private CourseIdMother id;
	private CourseTitleMother title;
	
	public static Course create() throws InvalidArgumentException {
		return new Course(CourseIdMother.random(), CourseTitleMother.random());
	}
}
