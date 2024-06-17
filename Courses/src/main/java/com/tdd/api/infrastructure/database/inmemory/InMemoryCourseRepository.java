package com.tdd.api.infrastructure.database.inmemory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.tdd.api.domain.course.Course;
import com.tdd.api.domain.course.CourseId;
import com.tdd.api.domain.course.CourseRepository;

public final class InMemoryCourseRepository implements CourseRepository {

	private List<Course> database = new ArrayList<>();

	@Override
	public Optional<Course> searchCourse(CourseId id) {
		return database.stream()
				.filter(c -> c.getIdValue().equals(id.getValue()))
				.findFirst();
	}

	@Override
	public void saveCourse(Course course) {
		database.add(course);
	}

	@Override
	public Optional<List<Course>> getAll() {
		return Optional.of(database);
	}
	
	// For testing purposes
	public void clear() {
		this.database.clear();
	}
}
