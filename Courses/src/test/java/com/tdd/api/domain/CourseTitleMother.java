package com.tdd.api.domain;

import com.github.javafaker.Faker;
import com.tdd.api.domain.course.CourseTitle;
import com.tdd.api.domain.exception.InvalidArgumentException;

final public class CourseTitleMother {
	public static CourseTitle random() throws InvalidArgumentException {
		return new CourseTitle(new Faker().lorem().sentence());
	}
}
