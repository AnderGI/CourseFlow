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
import com.tdd.api.application.find_course.CourseFinder;
import com.tdd.api.application.find_course.FindAllCoursesQuery;
import com.tdd.api.application.find_course.FindCourseQuery;
import com.tdd.api.application.find_course.FindCourseQueryHandler;
import com.tdd.api.domain.Course;
import com.tdd.api.domain.CourseId;
import com.tdd.api.domain.CourseNotExistError;
import com.tdd.api.domain.CourseRepository;
import com.tdd.api.domain.InvalidArgumentException;
import com.tdd.api.domain.QueryBus;
import com.tdd.api.domain.QueryHandler;
import com.tdd.api.infrastructure.bbdd.inmemory.InMemoryCourseRepository;
import com.tdd.api.infrastructure.bus.CourseQueryBus;
import com.tdd.api.infrastructure.jackson.parse_json.ExceptionToJsonNodeFactory;
import com.tdd.api.infrastructure.jackson.parse_json.JsonCourseParser;

@RestController
final public class GetCourseController {

	private final CourseRepository repo;
	private final JsonCourseParser courseParser;
	private final QueryBus queryBus;
	private final ObjectMapper mapper = new ObjectMapper();
	
    public GetCourseController() {
        this.repo = new InMemoryCourseRepository();
        this.courseParser = new JsonCourseParser();
        this.queryBus = new CourseQueryBus();
    }

	@GetMapping("/courses")
	public ResponseEntity<JsonNode> getAll() { 
		ObjectNode rootNode = mapper.createObjectNode();
		ArrayNode coursesArray = mapper.createArrayNode();
		List<Course> courses = repo.getAll().orElse(null);
		for(Course course : courses) {
			try {
				JsonNode jsonNodeCourse = courseParser.fromCourseToJson(course);
				coursesArray.add(jsonNodeCourse);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		rootNode.set("courses", coursesArray);
		return ResponseEntity.ok(rootNode);
	}

	
	@GetMapping("/courses/{id}")
	public ResponseEntity<JsonNode> getCourseByIdQuery(@PathVariable String id) {
		FindCourseQuery query = new FindCourseQuery(id);
        FindCourseQueryHandler findCourseHandler = new FindCourseQueryHandler(repo);
        queryBus.registerHandler(FindCourseQuery.class, findCourseHandler);
		Course course = null; 
		try {
			course = queryBus.ask(query);
		} catch (Exception exp) { //InvalidArgumentException, CourseNotExistError
			// TODO Auto-generated catch block
			return ResponseEntity.status(404).body(ExceptionToJsonNodeFactory.parse(exp));
		}
		
		try {
			return ResponseEntity.ok(courseParser.fromCourseToJson(course));
		} catch (Exception exp) {
			return ResponseEntity.status(404).body(ExceptionToJsonNodeFactory.parse(exp));
		}
	}
}
