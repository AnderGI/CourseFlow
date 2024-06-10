package com.tdd.api.infrastructure.controller.post;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.tdd.api.domain.events.DomainEventPublisher;
import com.tdd.api.infrastructure.bbdd.inmemory.InMemoryCourseRepository;
import com.tdd.api.infrastructure.events.rabbitmq.RabbitMqCourseEventPublisher;
import com.tdd.api.infrastructure.jackson.parse_json.ExceptionToJsonNodeFactory;
import com.tdd.api.infrastructure.jackson.parse_json.JsonCourseParser;

@RestController
public final class CoursePostWebController {
	
    private final CourseCommandBusSync bus;
    private final ObjectMapper mapper;
    private final CourseSaver courseSaver;
    private final CourseRepository repo;
    private final DomainEventPublisher rabbit;

    @Autowired
    public CoursePostWebController(CourseCommandBusSync bus, ObjectMapper mapper, 
    		CourseSaver courseSaver, CourseRepository repo, DomainEventPublisher rabbit) {
        this.bus = bus;
        this.mapper = mapper;
        this.courseSaver = courseSaver;
        this.repo = repo;
        this.rabbit = rabbit;
    }
	@PostMapping(path = "/courses", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<JsonNode> addCourseFormUrlEncoded(
			@RequestParam MultiValueMap<String, String> formCourseData) {
		Map<String, String> courseCommandMapper = this.getCourseRequiredCommandInformation(formCourseData);
		CreateCourseCommand command = new CreateCourseCommand(courseCommandMapper.get("id"), courseCommandMapper.get("title"));
		CourseCommandBusSync bus = new CourseCommandBusSync();
		CreateNewCourseCommandHandler handler = new CreateNewCourseCommandHandler(
				repo, new CourseSaver(repo, rabbit));
		bus.register(command.getClass(), handler);
		try {
			bus.dispatch(command);
		} catch (Exception exp) {
			System.out.println(exp);
			return ResponseEntity.unprocessableEntity().body(ExceptionToJsonNodeFactory.parse(exp));
		}

		ObjectNode successNode = mapper.createObjectNode();
		successNode.put("message", "New course created");
		return ResponseEntity.status(HttpStatusCode.valueOf(201)).body(successNode);
	}
	
	private Map<String, String> getCourseRequiredCommandInformation(MultiValueMap<String, String> formCourseData) {
		Map<String, String> courseCommandMapper = new HashMap<>();
		Set<Entry<String, List<String>>> entries = formCourseData.entrySet();
		String courseIdValue = null;
		String courseTitleValue = null;
		for (Entry<String, List<String>> entry : entries) {
			if (entry.getKey().equalsIgnoreCase("id")) {
				courseIdValue = entry.getValue().get(0);
			} else if (entry.getKey().equalsIgnoreCase("title")) {
				courseTitleValue = entry.getValue().get(0);
			}
		}
		courseCommandMapper.put("id", courseIdValue);
		courseCommandMapper.put("title", courseTitleValue);
		return courseCommandMapper;
	}

}
