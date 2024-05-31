package com.tdd.api.infrastructure.controller.post;

import java.net.URI;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tdd.api.application.bus.CourseCommandBusSync;
import com.tdd.api.application.find_course.CourseFinder;
import com.tdd.api.application.save_course.CourseSaver;
import com.tdd.api.application.save_course.CreateNewCourseCommandHandler;
import com.tdd.api.domain.Course;
import com.tdd.api.domain.CourseId;
import com.tdd.api.domain.CourseRepository;
import com.tdd.api.domain.command.CreateCourseCommand;
import com.tdd.api.infrastructure.bbdd.inmemory.InMemoryCourseRepository;
import com.tdd.api.infrastructure.jackson.parse_json.ExceptionToJsonNodeFactory;
import com.tdd.api.infrastructure.jackson.parse_json.JsonCourseParser;

@RestController
final public class CoursePostController {
	private static CourseRepository repo = new InMemoryCourseRepository();
	private static ObjectMapper mapper = new ObjectMapper();

	@PostMapping(path = "/courses", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<JsonNode> addCourseFormUrlEncoded(@RequestParam MultiValueMap<String, String> formCourseData){
		// {key = [value], key = [value]...}
		Set<Entry<String, List<String>>> entries = formCourseData.entrySet();
		String courseIdValue = null;
		String courseTitleValue = null;
		for(Entry<String, List<String>> entry : entries) {
			if(entry.getKey().equalsIgnoreCase("id")) {
				courseIdValue = entry.getValue().get(0);
			}else if(entry.getKey().equalsIgnoreCase("title")) {
				courseTitleValue = entry.getValue().get(0);
			}
		}
		
		CreateCourseCommand command = new CreateCourseCommand(courseIdValue, courseTitleValue);
		CourseCommandBusSync bus = new CourseCommandBusSync();
		CreateNewCourseCommandHandler handler = new CreateNewCourseCommandHandler(
				repo, new CourseSaver(repo));
		bus.register(command.getClass(), handler);
		try {
			bus.dispatch(command);
		} catch (Exception exp) {
			return ResponseEntity.unprocessableEntity().body(ExceptionToJsonNodeFactory.parse(exp));
		}
		
		ObjectNode successNode = mapper.createObjectNode();
		successNode.put("message", "New course created");
		return ResponseEntity.status(HttpStatusCode.valueOf(201)).body(successNode);
	}
	
}
