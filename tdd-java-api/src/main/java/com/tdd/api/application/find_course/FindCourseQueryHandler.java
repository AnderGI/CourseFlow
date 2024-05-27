package com.tdd.api.application.find_course;

import java.util.Optional;

import com.tdd.api.domain.Course;
import com.tdd.api.domain.CourseId;
import com.tdd.api.domain.CourseRepository;
import com.tdd.api.domain.exceptions.CourseNotExistError;
import com.tdd.api.domain.exceptions.InvalidArgumentException;
import com.tdd.api.domain.query.FindCourseQuery;
import com.tdd.api.domain.query.Query;
import com.tdd.api.domain.query.QueryHandler;

public class FindCourseQueryHandler implements QueryHandler<FindCourseQuery, Course> {

	private CourseRepository repo = null;
	
	public FindCourseQueryHandler(CourseRepository repo) {
		this.repo = repo;
	}
	
	@Override
	public Course handle(FindCourseQuery query) throws InvalidArgumentException, CourseNotExistError {
		// TODO Auto-generated method stub
		CourseId courseId = new CourseId(query.getCourseQueryId());
		CourseFinder finder = new CourseFinder(repo);
		return finder.findCourse(courseId);
	}

	


}
