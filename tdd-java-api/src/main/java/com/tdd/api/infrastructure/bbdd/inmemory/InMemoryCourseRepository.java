package com.tdd.api.infrastructure.bbdd.inmemory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.tdd.api.domain.Course;
import com.tdd.api.domain.CourseId;
import com.tdd.api.domain.CourseRepository;

@Repository
final public class InMemoryCourseRepository implements CourseRepository {
	private static List<Course> database = new ArrayList<>();

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


}
