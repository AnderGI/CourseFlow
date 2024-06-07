package com.tdd.api.infrastructure.controllers.get.get_all;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tdd.api.application.convert_reponse.CourseJsonResponseConverter;
import com.tdd.api.domain.Course;
import com.tdd.api.domain.CourseMother;
import com.tdd.api.domain.CourseRepository;
import com.tdd.api.infrastructure.bbdd.inmemory.InMemoryCourseRepository;

@WebMvcTest
public class GetAllCoursesList {
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper mapper;
	@MockBean
	private CourseRepository repository;



	@Test
	// Since it is atomic, courses need to be added first via POST
	void it_returns_a_json_of_existing_courses() throws Exception {
		// Given some random valid courses
		Course course1 = CourseMother.create();
		Course course2 = CourseMother.create();
		List<Course> addedCourses = Arrays.asList(course1, course2);
		CourseJsonResponseConverter converter = new CourseJsonResponseConverter();
		// Add to repository
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		MultiValueMap<String, String> course1FormData = new LinkedMultiValueMap<>();
		course1FormData.set("id", course1.getIdValue());
		course1FormData.set("title", course1.getTitleValue());
		mockMvc.perform(
				post("/courses")
				.headers(headers)
				.params(course1FormData)
		);
		MultiValueMap<String, String> course2FormData = new LinkedMultiValueMap<>();
		course2FormData.set("id", course2.getIdValue());
		course2FormData.set("title", course2.getTitleValue());
		mockMvc.perform(
				post("/courses")
				.headers(headers)
				.params(course2FormData)
		);
		// when
		when(repository.getAll()).thenReturn(Optional.of(addedCourses));
		// perform get all
		mockMvc.perform(get("/courses").accept(MediaType.APPLICATION_JSON_VALUE))
		.andExpectAll(
				status().isOk(),
				content().contentType(MediaType.APPLICATION_JSON_VALUE),
				content().json(((JsonNode) converter.convertAll(addedCourses)).toString())
		);
	}

}
