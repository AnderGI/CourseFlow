package com.tdd.api.infrastructure.controllers.get.get_all;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tdd.api.application.convert_reponse.CourseJsonResponseConverter;
import com.tdd.api.domain.Course;
import com.tdd.api.domain.CourseRepository;
import com.tdd.api.infrastructure.controller.get.GetCourseController;

@WebMvcTest
public class GetAllCoursesWebMvcAcceptanceTets {
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private GetCourseController getController;
	@Autowired
	private ObjectMapper mapper;
	@MockBean
	private CourseRepository repository;
	
	@Test
	void it_returns_an_empty_list_of_courses() throws Exception {
		// Given
		// There are no courses
		List<Course> courses = new ArrayList<>();
		CourseJsonResponseConverter converter = new CourseJsonResponseConverter();
		// When
		when(repository.getAll()).thenReturn(Optional.of(courses));
		System.out.println((JsonNode) converter.convertAll(courses));
		// Then
		mockMvc.perform(
				get("/courses").accept(MediaType.APPLICATION_JSON_VALUE)
		)
		.andDo(print())
		.andExpectAll(
				status().isOk(),
				content().contentType(MediaType.APPLICATION_JSON),
				content().json(
						((JsonNode) converter.convertAll(courses)).toString()
				)
		);
	}
	
}
