package com.tdd.api.application.converters.response;

import java.util.List;

import org.springframework.stereotype.Component;

import com.tdd.api.domain.course.Course;

// Generic methods to ensure different implementations for different formats
// each result format one specific R

public interface ResponseConverter {
	<R> R convertSingle(Course course);
	<R> R convertAll(List<Course> courses);
}
