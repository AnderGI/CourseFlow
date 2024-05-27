package com.tdd.api.application.find_course;

import java.util.ArrayList;
import java.util.List;

import com.tdd.api.domain.Course;
import com.tdd.api.domain.CourseId;
import com.tdd.api.domain.CourseNotExistError;
import com.tdd.api.domain.CourseRepository;

// As a use case
// it needs to have a repository
// I will do that by applying DIP 
// Working against an abstraction not an implementation
final public class CourseFinder {

	private CourseRepository repository;
	
	public CourseFinder(CourseRepository repository) {
		this.repository = repository;
	}

	public Course findCourse(CourseId id) throws CourseNotExistError {
		Course course = repository.searchCourse(id).orElse(null);
		if(course == null) {
			throw new CourseNotExistError(id);
		}
		return course;
	}
	
	public List<Course> findAll(){
		return repository.getAll().orElse(null);
	}

	
	
}
