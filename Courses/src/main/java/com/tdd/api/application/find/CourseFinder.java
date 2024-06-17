package com.tdd.api.application.find;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tdd.api.domain.course.Course;
import com.tdd.api.domain.course.CourseId;
import com.tdd.api.domain.course.CourseRepository;
import com.tdd.api.domain.exception.CourseNotExistException;

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

	public Course findCourse(CourseId id) throws CourseNotExistException {
		Course course = repository.searchCourse(id).orElse(null);
		if(course == null) {
			throw new CourseNotExistException(id);
		}
		return course;
	}
	
	public List<Course> findAll(){
		return repository.getAll().orElse(null);
	}

	
	
}
