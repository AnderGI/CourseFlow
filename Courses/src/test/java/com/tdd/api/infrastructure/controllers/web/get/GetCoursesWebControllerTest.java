package com.tdd.api.infrastructure.controllers.web.get;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tdd.api.TestingConfig;
import com.tdd.api.application.converters.exception.ExceptionToJsonNodeFactory;
import com.tdd.api.application.converters.response.ResponseConverter;
import com.tdd.api.application.converters.response.json.CourseJsonResponseConverter;
import com.tdd.api.domain.CourseIdMother;
import com.tdd.api.domain.CourseMother;
import com.tdd.api.domain.course.Course;
import com.tdd.api.domain.course.CourseId;
import com.tdd.api.domain.course.CourseRepository;
import com.tdd.api.domain.exception.CourseNotExistException;
import com.tdd.api.infrastructure.database.inmemory.InMemoryCourseRepository;

@WebMvcTest
@ActiveProfiles("testing")
@Import(TestingConfig.class)
public class GetCoursesWebControllerTest {

	@Autowired
	private ObjectMapper mapper;
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	// Notr MockBean because we are doing acceptance / e2e testing
	private CourseRepository repository;
	private final ExceptionToJsonNodeFactory exceptionToJsonFactory = new ExceptionToJsonNodeFactory();

	@Test
	void repository_should_be_instantiated() {
		assertNotNull(repository);
	}

	@Test
	void repository_should_have_correct_class_instance() {
		assertEquals(repository.getClass(), InMemoryCourseRepository.class);
	}

	@Test
	void get_all_courses_when_empty_json_response() throws Exception {
		// Given there is an empty repository
		((InMemoryCourseRepository) repository).clear();
		ResponseConverter converter = this.givenJsonResponseConverter();
		// Then
		mockMvc.perform(get("/courses")).andDo(print()).andExpectAll(status().isOk(),
				header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE),
				content().contentType(MediaType.APPLICATION_JSON),
				content().json(converter.convertAll(repository.getAll().orElse(null)).toString()));
	}

	@Test
	void get_all_courses_when_not_empty_json_response() throws Exception {
		// Given there is an empty repository
		((InMemoryCourseRepository) repository).clear();
		ResponseConverter converter = this.givenJsonResponseConverter();
		repository.saveCourse(CourseMother.create());
		repository.saveCourse(CourseMother.create());
		repository.saveCourse(CourseMother.create());
		// Then
		mockMvc.perform(get("/courses")).andDo(print()).andExpectAll(status().isOk(),
				header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE),
				content().contentType(MediaType.APPLICATION_JSON),
				content().json(converter.convertAll(repository.getAll().orElse(null)).toString()));
	}

	@Test
	void get_existing_course_by_id_json_response() throws Exception {
		((InMemoryCourseRepository) repository).clear();
		ResponseConverter converter = this.givenJsonResponseConverter();
		Course course = CourseMother.create();
		String idValue = course.getIdValue();
		CourseId courseId = CourseIdMother.fromValue(idValue);
		repository.saveCourse(course);
		// Then
		mockMvc.perform(get("/courses/{id}", idValue)).andDo(print()).andExpectAll(status().isOk(),
				header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE),
				content().contentType(MediaType.APPLICATION_JSON),
				content().json(converter.convertSingle(course).toString()));
	}

	@Test
	void not_get_not_existing_course_json_response() throws Exception {
		((InMemoryCourseRepository) repository).clear();
		ResponseConverter converter = this.givenJsonResponseConverter();
		CourseId courseId = CourseIdMother.random();
		// Then
		mockMvc
		.perform(get("/courses/{id}", courseId.getValue()))
		.andDo(print())
		.andExpectAll(status().isNotFound(),
				header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE),
				content().contentType(MediaType.APPLICATION_JSON),
				content().json(exceptionToJsonFactory.parse(new CourseNotExistException(courseId)).toString()));
	}
	
	private ResponseConverter givenJsonResponseConverter() {
		return new CourseJsonResponseConverter();
	}

}
