package com.tdd.api.bdd.stepsdefs.add_course;


import static org.junit.Assert.assertEquals;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class PostCourseSteps {
	private RestTemplate rest = new RestTemplate();
	private Integer port = 3001;
	private ObjectMapper mapper = new ObjectMapper();
	private JsonNode receivedCourseInJson = null;
	private ResponseEntity<JsonNode> response = null;

	// Happy path for existing course
	@Given("a user sends a POST request with new course")
	public void a_user_sends_a_post_request_with_new_course(String jsonRepresentedCourse) {
		try{
			receivedCourseInJson = mapper.readTree(jsonRepresentedCourse);
		}catch(JsonProcessingException exp){
		}
	}

	@When("course is added to database in {string}")
	public void course_is_added_to_database(String coursePostEndpoint) {
		response = rest.postForEntity("http://localhost:" + port + coursePostEndpoint, receivedCourseInJson, JsonNode.class);
	}

	@Then("response status code for added course is {int} created")
	public void the_response_status_code_should_be(int statusCode) {
		assertEquals(statusCode, response.getStatusCode().value());
	}

	@Then("response content is :")
	public void the_response_content_should_be(String expectedContent) {
		JsonNode expectedJsonNode = null;
		try {
			expectedJsonNode = mapper.readTree(expectedContent);
		} catch (Exception exp) {
			exp.printStackTrace();
		}
		assertEquals(expectedJsonNode, response.getBody());
	}

	

}