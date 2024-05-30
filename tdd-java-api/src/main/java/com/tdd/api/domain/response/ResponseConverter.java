package com.tdd.api.domain.response;

import java.util.List;

import com.tdd.api.domain.Course;

// Generic methods to ensure different implementations for different formats
// each result format one specific R
public interface ResponseConverter {
	<R> R convertSingle(Course course);
	<R> R convertAll(List<Course> courses);
}
