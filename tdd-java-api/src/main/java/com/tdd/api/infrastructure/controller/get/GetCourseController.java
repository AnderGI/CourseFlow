package com.tdd.api.infrastructure.controller.get;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tdd.api.application.find_course.CourseFinder;
import com.tdd.api.domain.Course;
import com.tdd.api.domain.CourseId;
import com.tdd.api.domain.CourseNotExistError;
import com.tdd.api.domain.CourseRepository;
import com.tdd.api.domain.InvalidArgumentException;
import com.tdd.api.infrastructure.bbdd.inmemory.InMemoryCourseRepository;
import com.tdd.api.infrastructure.jackson.parse_json.JsonCourseParser;
import com.tdd.api.infrastructure.jackson.parse_json.JsonErrorParser;



@RestController
final public class GetCourseController {
	
	private static CourseRepository repo = new InMemoryCourseRepository();
	private static JsonErrorParser errorParser = new JsonErrorParser();
	private static JsonCourseParser courseParser = new JsonCourseParser();
	public GetCourseController() {
		
	}
	
	@GetMapping("/courses")
	public ResponseEntity<List<Course>> getAll(){
		return ResponseEntity.of(repo.getAll());
	}
	
	@GetMapping("/courses/{id}")
	public ResponseEntity<JsonNode> getCourseById(@PathVariable String id){
		CourseFinder finder = new CourseFinder(repo);
		Course course = null;
		try {
			course = finder.findCourse(new CourseId(id));
		} catch (Exception exp) {
			return ResponseEntity.status(404).body(errorParser.fromExceptionToJson(exp));
		}
		
		try {
			return ResponseEntity.ok(courseParser.fromCourseToJson(course));
		}catch(Exception exp) {
			return ResponseEntity.status(404).body(errorParser.fromExceptionToJson(exp));
		}
		
	}
}
