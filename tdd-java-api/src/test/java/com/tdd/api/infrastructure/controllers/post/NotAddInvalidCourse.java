package com.tdd.api.infrastructure.controllers.post;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tdd.api.domain.Course;
import com.tdd.api.domain.CourseMother;


@WebMvcTest
public class NotAddInvalidCourse {
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper mapper;
	
	@Test
	void it_should_add_a_valid_course() throws Exception{
		Course newCourse = CourseMother.create();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("id", newCourse.getIdValue());
		ObjectNode postErrorNode = mapper.createObjectNode();
		postErrorNode.put("error", "422 Unprocessable Entity");
		postErrorNode.put("message", "Invalid arguments for course creation");
		mockMvc.perform(
				post("/courses")
				.headers(headers)
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.params(params)
		)
		.andExpectAll(
				status().isUnprocessableEntity(),
				content().contentType(MediaType.APPLICATION_JSON),
				content().json(postErrorNode.toString())
		)
		;
	}
}