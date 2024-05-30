package com.tdd.api.application.find_course;

import java.util.Optional;

import com.fasterxml.jackson.databind.JsonNode;
import com.tdd.api.application.convert_reponse.CourseJsonResponseConverter;
import com.tdd.api.domain.Course;
import com.tdd.api.domain.CourseId;
import com.tdd.api.domain.CourseRepository;
import com.tdd.api.domain.exceptions.CourseNotExistError;
import com.tdd.api.domain.exceptions.InvalidArgumentException;
import com.tdd.api.domain.query.FindCourseQuery;
import com.tdd.api.domain.query.Query;
import com.tdd.api.domain.query.QueryHandler;

// json Node should be some kind of videoresponse
public class FindCourseQueryHandler implements QueryHandler<FindCourseQuery, JsonNode> {

	private CourseRepository repo = null;
	
	public FindCourseQueryHandler(CourseRepository repo) {
		this.repo = repo;
	}
	
	@Override
	public JsonNode handle(FindCourseQuery query) throws InvalidArgumentException, CourseNotExistError {
		// TODO Auto-generated method stub
		CourseId courseId = new CourseId(query.getCourseQueryId());
		CourseFinder finder = new CourseFinder(repo);
		// should be injected -> DIP
		CourseJsonResponseConverter converter = new CourseJsonResponseConverter(); 
		Course foundCourse = finder.findCourse(courseId);
		return converter.convertSingle(foundCourse);
	}

	


}
