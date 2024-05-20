package com.tdd.api.infrastructure;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.tdd.api.domain.Course;
import com.tdd.api.domain.CourseId;
import com.tdd.api.domain.CourseRepository;


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
		// TODO Auto-generated method stub
		database.add(course);		
	}


	@Override
	public List<Course> getAll() {
		// TODO Auto-generated method stub
		return database;
	}

}
