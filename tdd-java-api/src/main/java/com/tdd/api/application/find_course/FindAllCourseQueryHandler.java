package com.tdd.api.application.find_course;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.tdd.api.application.convert_reponse.CourseJsonResponseConverter;
import com.tdd.api.domain.Course;
import com.tdd.api.domain.CourseRepository;
import com.tdd.api.domain.query.FindAllCoursesQuery;
import com.tdd.api.domain.query.QueryHandler;
import com.tdd.api.domain.response.ResponseConverter;

public class FindAllCourseQueryHandler implements QueryHandler<FindAllCoursesQuery, JsonNode> {

	private CourseRepository repo = null;
	private ResponseConverter converter = null;
	private CourseFinder finder = null;
	
	public FindAllCourseQueryHandler(CourseRepository repo, CourseFinder finder, ResponseConverter converter) {
		this.repo = repo;
		this.converter = converter;
		this.finder = finder;
	}
	
	@Override
	public JsonNode handle(FindAllCoursesQuery query) throws Exception {
		return converter.convertAll(finder.findAll());
	}

}
