package com.tdd.api.infrastructure.controllers.web.post;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tdd.api.application.converters.exception.ExceptionToJsonNodeFactory;
import com.tdd.api.application.converters.response.ResponseConverter;
import com.tdd.api.application.find.CourseFinder;
import com.tdd.api.application.find.CourseQueryBusSync;
import com.tdd.api.application.save.CourseCommandBusSync;
import com.tdd.api.application.save.CourseSaver;
import com.tdd.api.application.save.command.CreateCourseCommand;
import com.tdd.api.application.save.command.CreateNewCourseCommandHandler;
import com.tdd.api.domain.command.CommandBus;
import com.tdd.api.domain.course.CourseRepository;
import com.tdd.api.domain.events.DomainEventPublisher;

@RestController
@RequestMapping("/courses")
public class PostCoursesWebController {

	@Autowired
	private ObjectMapper mapper;
	private final CourseRepository repository;
	private final CourseSaver finder;
	private final ResponseConverter converter;
	private final CommandBus queryBus;
    private final DomainEventPublisher publisher;
	
	public PostCoursesWebController(
			CourseRepository repository, ResponseConverter converter,
			DomainEventPublisher publisher) {
		this.repository = repository;
		this.publisher = publisher;
		this.finder = new CourseSaver(repository, publisher);
		this.converter = converter;
		this.queryBus = new CourseCommandBusSync();
	}
	
	@PostMapping(path = "", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<JsonNode> addCourse(
			@RequestParam MultiValueMap<String, String> formCourseData) {
		// Directly create command from form multivaluemap ¿?
		Map<String, String> courseCommandMapper = this.getCourseRequiredCommandInformation(formCourseData);
		CreateCourseCommand command = new CreateCourseCommand(courseCommandMapper.get("id"), courseCommandMapper.get("title"));
		CreateNewCourseCommandHandler handler = new CreateNewCourseCommandHandler(
				repository, new CourseSaver(repository, publisher));
		queryBus.register(command.getClass(), handler);
		try {
			queryBus.dispatch(command);
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
