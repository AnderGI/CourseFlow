package com.tdd.api.application.find_course;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.tdd.api.application.convert_reponse.CourseJsonResponseConverter;
import com.tdd.api.domain.Course;
import com.tdd.api.domain.CourseRepository;
import com.tdd.api.domain.query.FindAllCoursesQuery;
import com.tdd.api.domain.query.QueryHandler;

public class FindAllCourseQueryHandler implements QueryHandler<FindAllCoursesQuery, JsonNode> {

	private CourseRepository repo = null;
	
	public FindAllCourseQueryHandler(CourseRepository repo) {
		this.repo = repo;
	}
	
	@Override
	public JsonNode handle(FindAllCoursesQuery query) throws Exception {
		// TODO Auto-generated method stub
		CourseFinder finder = new CourseFinder(repo);
		
		List<Course> courses =  finder.findAll();
		// should apply dip
		CourseJsonResponseConverter converter = new CourseJsonResponseConverter(); 
		return converter.convertAll(courses);
	}

}
