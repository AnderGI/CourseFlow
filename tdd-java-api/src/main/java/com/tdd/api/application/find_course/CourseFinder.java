package com.tdd.api.application.find_course;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tdd.api.domain.Course;
import com.tdd.api.domain.CourseId;
import com.tdd.api.domain.CourseRepository;
import com.tdd.api.domain.exceptions.CourseNotExistError;

// As a use case
// it needs to have a repository
// I will do that by applying DIP 
// Working against an abstraction not an implementation
@Service
public final class CourseFinder {

	private CourseRepository repository;
	
	@Autowired
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
