package com.tdd.api.application.find.query.find_one;

import com.fasterxml.jackson.databind.JsonNode;
import com.tdd.api.application.converters.response.ResponseConverter;
import com.tdd.api.application.find.CourseFinder;
import com.tdd.api.domain.course.CourseId;
import com.tdd.api.domain.course.CourseRepository;
import com.tdd.api.domain.exception.CourseNotExistException;
import com.tdd.api.domain.exception.InvalidArgumentException;
import com.tdd.api.domain.query.QueryHandler;

// json Node should be some kind of videoresponse
public final class FindCourseQueryHandler implements QueryHandler<FindCourseQuery, JsonNode> {

	private CourseRepository repo = null;
	private ResponseConverter converter = null;
	private CourseFinder finder = null;
	
	public FindCourseQueryHandler(CourseRepository repo, ResponseConverter converter, CourseFinder finder) {
		this.repo = repo;
		this.converter = converter;
		this.finder = finder;
	}
	
	@Override
	public JsonNode handle(FindCourseQuery query) throws InvalidArgumentException, CourseNotExistException {
		// TODO Auto-generated method stub
		CourseId courseId = new CourseId(query.getCourseQueryId());
		return converter.convertSingle(finder.findCourse(courseId));
	}

	


}
