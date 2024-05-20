package com.tdd.api.infrastructure.controller.post;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tdd.api.application.find_course.CourseFinder;
import com.tdd.api.application.save_course.CourseSaver;
import com.tdd.api.domain.Course;
import com.tdd.api.domain.CourseId;
import com.tdd.api.domain.CourseNotExistError;
import com.tdd.api.domain.CourseRepository;
import com.tdd.api.domain.InvalidArgumentException;
import com.tdd.api.infrastructure.InMemoryCourseRepository;

@RestController
final public class CoursePostController {
	private CourseRepository repo = new InMemoryCourseRepository();
	
	@PostMapping(path = "/courses", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> addCourse(@RequestBody Course course) {
		CourseSaver saver = new CourseSaver(repo);
		saver.saveCourse(course);
		CourseFinder finder = new CourseFinder(repo);
		Course toFind = null;
		try {
			toFind = finder.findCourse(new CourseId(course.getIdValue()));
		} catch (CourseNotExistError | InvalidArgumentException e) {
			// TODO Auto-generated catch block
			return ResponseEntity.unprocessableEntity().build();
		}
		
		URI uri = null;
		try {
			uri = new URI("/courses/" + toFind.getIdValue());
			return ResponseEntity.created(uri).build();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			return ResponseEntity.badRequest().build();
		} 
		
		
	}
}
