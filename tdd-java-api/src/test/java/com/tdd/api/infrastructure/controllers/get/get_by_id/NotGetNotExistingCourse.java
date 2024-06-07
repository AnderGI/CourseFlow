package com.tdd.api.infrastructure.controllers.get.get_by_id;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tdd.api.domain.CourseId;
import com.tdd.api.domain.CourseIdMother;
import com.tdd.api.domain.CourseRepository;

@WebMvcTest
public class NotGetNotExistingCourse {
/*
    {
        "error" : "404 Not Found", 
        "message" : "Specified course not found"} 
 */
	
	@MockBean
	private CourseRepository repository;
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper mapper;
	
	@Test
	void it_should_not_get_a_non_existing_course() throws Exception{
		CourseId fakeId = CourseIdMother.random();
		ObjectNode responseError = mapper.createObjectNode();
		responseError.put("error", "404 Not Found");
		responseError.put("message", "Specified course not found");
		mockMvc.perform(
				get("/courses/{id}", fakeId.getValue())
		)
		.andDo(print())
		.andExpectAll(
				status().isNotFound(),
				content().contentType(MediaType.APPLICATION_JSON_VALUE),
				content().json(responseError.toString())
		);
	}
	
}
