package com.tdd.api.domain;

import java.util.List;
import java.util.Optional;

public interface CourseRepository {
	Optional<Course> searchCourse(CourseId id);
	void saveCourse(Course course);
	List<Course> getAll();
}
