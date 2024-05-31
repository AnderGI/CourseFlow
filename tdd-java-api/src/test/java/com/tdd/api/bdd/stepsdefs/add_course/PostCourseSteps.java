package com.tdd.api.bdd.stepsdefs.add_course;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.cucumber.datatable.DataTable;

public class PostCourseSteps {
	private RestTemplate rest = new RestTemplate();
	private Integer port = 3001;
	private ObjectMapper mapper = new ObjectMapper();
	private JsonNode receivedCourseInJson = null;
	private ResponseEntity<JsonNode> response = null;
	private final String DOMAIN_URL = "http://localhost:" + port;

	// Happy path for existing course
	@Given("a user sends a POST request with new course")
	public void a_user_sends_a_post_request_with_new_course(DataTable courseDatatable) {
		Map<String, String> map = courseDatatable.asMap(String.class, String.class);
		MultiValueMap<String, String> courseFormData = new LinkedMultiValueMap<>();
		courseFormData.set("id", map.get("id"));
		courseFormData.set("title", map.get("title"));
		// For posting via form url encoded data
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(
				courseFormData, headers);

		response = rest.postForEntity(DOMAIN_URL + "/courses", request, JsonNode.class);
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