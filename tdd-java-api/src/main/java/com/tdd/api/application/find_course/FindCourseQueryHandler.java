package com.tdd.api.application.find_course;

import java.util.Optional;

import com.fasterxml.jackson.databind.JsonNode;
import com.tdd.api.application.convert.entity_to_json.CourseJsonResponseConverter;
import com.tdd.api.domain.Course;
import com.tdd.api.domain.CourseId;
import com.tdd.api.domain.CourseRepository;
import com.tdd.api.domain.exceptions.CourseNotExistError;
import com.tdd.api.domain.exceptions.InvalidArgumentException;
import com.tdd.api.domain.query.FindCourseQuery;
import com.tdd.api.domain.query.Query;
import com.tdd.api.domain.query.QueryHandler;
import com.tdd.api.domain.response.ResponseConverter;

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
	public JsonNode handle(FindCourseQuery query) throws InvalidArgumentException, CourseNotExistError {
		// TODO Auto-generated method stub
		CourseId courseId = new CourseId(query.getCourseQueryId());
		return converter.convertSingle(finder.findCourse(courseId));
	}

	


}
