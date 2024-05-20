package com.tdd.api.infrastructure.controller.get;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tdd.api.application.find_course.CourseFinder;
import com.tdd.api.domain.Course;
import com.tdd.api.domain.CourseId;
import com.tdd.api.domain.CourseNotExistError;
import com.tdd.api.domain.CourseRepository;
import com.tdd.api.domain.InvalidArgumentException;
import com.tdd.api.domain.parse_to_json.JsonParser;
import com.tdd.api.infrastructure.InMemoryCourseRepository;



@RestController
final public class GetCourseController {
	
	private CourseRepository repo = new InMemoryCourseRepository();
	
	public GetCourseController() {
		
	}
	
	@GetMapping("/courses")
	public List<Course> getAll(){
		return repo.getAll();
	}
	
	@GetMapping("/courses/{id}")
	public ResponseEntity<ObjectNode> getCourseById(@PathVariable String id){
		CourseFinder finder = new CourseFinder(repo);
		try {
			
			Course course = finder.findCourse(new CourseId(id));
			JsonParser parser = new JsonParser();
			return ResponseEntity.ok(parser.createJsonCourseResponse(course));
		} catch (CourseNotExistError | InvalidArgumentException e) {
			// TODO Auto-generated catch block
			
			return ResponseEntity.notFound().build();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResponseEntity.notFound().build();
		}
		
	}
}
