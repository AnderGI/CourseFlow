package com.tdd.api.application.find_course;

import java.util.List;

import com.tdd.api.domain.Course;
import com.tdd.api.domain.CourseRepository;
import com.tdd.api.domain.query.FindAllCoursesQuery;
import com.tdd.api.domain.query.QueryHandler;

public class FindAllCourseQueryHandler implements QueryHandler<FindAllCoursesQuery, List<Course>> {

	private CourseRepository repo = null;
	
	public FindAllCourseQueryHandler(CourseRepository repo) {
		this.repo = repo;
	}
	
	@Override
	public List<Course> handle(FindAllCoursesQuery query) throws Exception {
		// TODO Auto-generated method stub
		CourseFinder finder = new CourseFinder(repo);
		
		return finder.findAll();
	}

}
