package com.tdd.api.infrastructure.controller.get;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tdd.api.application.bus.CourseQueryBusSync;
import com.tdd.api.application.convert.entity_to_json.CourseJsonResponseConverter;
import com.tdd.api.application.find_course.CourseFinder;
import com.tdd.api.application.find_course.FindAllCourseQueryHandler;
import com.tdd.api.application.find_course.FindCourseQueryHandler;
import com.tdd.api.domain.Course;
import com.tdd.api.domain.CourseId;
import com.tdd.api.domain.CourseRepository;
import com.tdd.api.domain.exceptions.CourseNotExistError;
import com.tdd.api.domain.exceptions.InvalidArgumentException;
import com.tdd.api.domain.query.FindAllCoursesQuery;
import com.tdd.api.domain.query.FindCourseQuery;
import com.tdd.api.domain.query.QueryBus;
import com.tdd.api.domain.query.QueryHandler;
import com.tdd.api.infrastructure.bbdd.inmemory.InMemoryCourseRepository;
import com.tdd.api.infrastructure.jackson.parse_json.ExceptionToJsonNodeFactory;
import com.tdd.api.infrastructure.jackson.parse_json.JsonCourseParser;

@RestController 
public final class GetCourseController {

	private final CourseRepository repo;
	private final JsonCourseParser courseParser;
	private final QueryBus queryBus;
	private final ObjectMapper mapper = new ObjectMapper();

	public GetCourseController() {
		this.repo = new InMemoryCourseRepository();
		this.courseParser = new JsonCourseParser();
		this.queryBus = new CourseQueryBusSync();
	}

	@GetMapping("/courses")
	public ResponseEntity<JsonNode> getAll() {
		FindAllCoursesQuery query = new FindAllCoursesQuery();
		
		FindAllCourseQueryHandler queryHandler = new FindAllCourseQueryHandler(
				repo, new CourseFinder(repo), new CourseJsonResponseConverter());
		
		queryBus.registerHandler(FindAllCoursesQuery.class, queryHandler);
		try {
			return ResponseEntity.ok(queryBus.ask(query));
		} catch (Exception exp) {
			// TODO Auto-generated catch block
			return ResponseEntity.status(404).body(ExceptionToJsonNodeFactory.parse(exp));
		}
	}

	@GetMapping("/courses/{id}")
	public ResponseEntity<JsonNode> getCourseById(@PathVariable String id) {
		FindCourseQuery query = new FindCourseQuery(id);
		FindCourseQueryHandler findCourseHandler = new FindCourseQueryHandler(
				repo, new CourseJsonResponseConverter(), new CourseFinder(repo));
		queryBus.registerHandler(FindCourseQuery.class, findCourseHandler);
		try {
			return ResponseEntity.ok(queryBus.ask(query));
		} catch (Exception exp) {
			return ResponseEntity.status(404).body(ExceptionToJsonNodeFactory.parse(exp));
		}
	}

}