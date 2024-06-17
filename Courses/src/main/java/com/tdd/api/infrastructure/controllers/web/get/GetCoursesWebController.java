package com.tdd.api.infrastructure.controllers.web.get;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.tdd.api.application.converters.exception.ExceptionToJsonNodeFactory;
import com.tdd.api.application.converters.response.ResponseConverter;
import com.tdd.api.application.find.CourseFinder;
import com.tdd.api.application.find.CourseQueryBusSync;
import com.tdd.api.application.find.query.find_all.FindAllCourseQueryHandler;
import com.tdd.api.application.find.query.find_all.FindAllCoursesQuery;
import com.tdd.api.application.find.query.find_one.FindCourseQuery;
import com.tdd.api.application.find.query.find_one.FindCourseQueryHandler;
import com.tdd.api.domain.course.CourseRepository;

@RestController
@RequestMapping("/courses")
public final class GetCoursesWebController {
	
	private final CourseRepository repository;
	private final CourseFinder finder;
	private final ResponseConverter converter;
	private final CourseQueryBusSync queryBus;
	public GetCoursesWebController(
			CourseRepository repository, ResponseConverter converter) {
		this.repository = repository;
		this.finder = new CourseFinder(repository);
		this.converter = converter;
		this.queryBus = new CourseQueryBusSync();
	}
	
	@GetMapping("")
	public ResponseEntity<JsonNode> getAll() {
		// Create get al query
		FindAllCoursesQuery findAllQuery = new FindAllCoursesQuery();
		// pass to bus
		// bus registers with its ocrresponding handler
		FindAllCourseQueryHandler allCoursesHandler = new FindAllCourseQueryHandler(repository, finder, converter);
		queryBus.registerHandler(FindAllCoursesQuery.class, allCoursesHandler);
		// handler calls service
		// service returns a response
		// render response 
		// if exception thrown catch it and render the correct response
		try {
			return ResponseEntity.ok(queryBus.ask(findAllQuery));
		} catch (Exception exp) {
			// TODO Auto-generated catch block
			return ResponseEntity.status(404).body(ExceptionToJsonNodeFactory.parse(exp));
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<JsonNode> getById(@PathVariable String id) {
		// Create the query
		FindCourseQuery findByIdQuery = new FindCourseQuery(id);
		// Create Handler
		FindCourseQueryHandler findByIdHandler = new FindCourseQueryHandler(repository, converter, finder);
		// Register
		queryBus.registerHandler(FindCourseQuery.class ,findByIdHandler);
		try {
			return ResponseEntity.ok(queryBus.ask(findByIdQuery));
		} catch (Exception exp) {
			// TODO Auto-generated catch block
			return ResponseEntity.status(404).body(ExceptionToJsonNodeFactory.parse(exp));
		}
	}
}
