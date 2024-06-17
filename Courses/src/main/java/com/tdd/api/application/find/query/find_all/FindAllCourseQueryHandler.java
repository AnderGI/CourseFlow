package com.tdd.api.application.find.query.find_all;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.JsonNode;
import com.tdd.api.application.converters.response.ResponseConverter;
import com.tdd.api.application.find.CourseFinder;
import com.tdd.api.domain.course.CourseRepository;
import com.tdd.api.domain.query.QueryHandler;

public final class FindAllCourseQueryHandler implements QueryHandler<FindAllCoursesQuery, JsonNode> {

	private CourseRepository repo = null;
	private ResponseConverter converter = null;
	private CourseFinder finder = null;
	
	@Autowired
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
