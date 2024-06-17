package com.tdd.api.domain;

import java.util.UUID;

import com.tdd.api.domain.course.CourseId;
import com.tdd.api.domain.exception.InvalidArgumentException;

final public class CourseIdMother {
	public static CourseId random() throws InvalidArgumentException {
		return new CourseId(UUID.randomUUID().toString());
	}

	public static CourseId fromValue(String value) throws InvalidArgumentException {
		return new CourseId(value);
	}
}
