package com.tdd.api.domain;

import com.github.javafaker.Faker;
import com.tdd.api.domain.exceptions.InvalidArgumentException;

final public class CourseTitleMother {
	public static CourseTitle random() throws InvalidArgumentException {
		return new CourseTitle(new Faker().lorem().sentence());
	}
}
