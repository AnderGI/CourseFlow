package com.tdd.api.infrastructure.controller.post;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tdd.api.application.find_course.CourseFinder;
import com.tdd.api.application.save_course.CourseSaver;
import com.tdd.api.domain.Course;
import com.tdd.api.domain.CourseId;
import com.tdd.api.domain.CourseNotExistError;
import com.tdd.api.domain.CourseRepository;
import com.tdd.api.domain.InvalidArgumentException;
import com.tdd.api.infrastructure.bbdd.inmemory.InMemoryCourseRepository;
import com.tdd.api.infrastructure.jackson.parse_json.JsonCourseParser;
import com.tdd.api.infrastructure.jackson.parse_json.JsonErrorParser;

@RestController
final public class CoursePostController {
	private static CourseRepository repo = new InMemoryCourseRepository();
	private static ObjectMapper mapper = new ObjectMapper();
	private static JsonErrorParser errorParser = new JsonErrorParser();
	
	@PostMapping(path = "/courses", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JsonNode> addCourse(@RequestBody JsonNode course) {
		// The JSON message to course domain
		JsonCourseParser parser = new JsonCourseParser();
		Course newCourse = null;
		try {
			newCourse = parser.fromJsonToCourse(course); 
		}catch(Exception exc) {
			return ResponseEntity.unprocessableEntity().body(errorParser.fromExceptionToJson(exc));
		}
		
		// If ok save it
		CourseSaver saver = new CourseSaver(repo);
		saver.saveCourse(newCourse);
		
		// Return the id in the location in responde header
		CourseFinder finder = new CourseFinder(repo);
		Course toFind = null;
		try {
			// CourseNotExistError -> when finding and repository returns null
			// InvalidArgumentException -> when instantiating courseid
			toFind = finder.findCourse(new CourseId(newCourse.getIdValue()));
		} catch (Exception exc) { // handle all errors
			return ResponseEntity.unprocessableEntity().body(errorParser.fromExceptionToJson(exc));
		}
		
		URI uri = null;
		try {
			uri = new URI("/courses/" + toFind.getIdValue());
		} catch (Exception exc) {
			return ResponseEntity.unprocessableEntity().body(errorParser.fromExceptionToJson(exc));
		}
		ObjectNode successNode = mapper.createObjectNode();
		successNode.put("message", "New course created with id " + newCourse.getIdValue());
		// TODO Auto-generated catch block
		return ResponseEntity.created(uri).body(successNode);
		
		
		
	}
}
