package com.tdd.api.domain.course;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;


public interface CourseRepository {
	Optional<Course> searchCourse(CourseId id);
	void saveCourse(Course course);
	Optional<List<Course>> getAll();
}
