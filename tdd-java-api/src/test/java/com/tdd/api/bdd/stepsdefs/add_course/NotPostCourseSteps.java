package com.tdd.api.bdd.stepsdefs.add_course;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tdd.api.domain.Course;
import com.tdd.api.domain.exceptions.InvalidArgumentException;
import com.tdd.api.infrastructure.jackson.parse_json.JsonCourseParser;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.cucumber.datatable.DataTable;

public class NotPostCourseSteps {
	private RestTemplate rest = new RestTemplate();
	private Integer port = 3001;
	private ObjectMapper mapper = new ObjectMapper();
	private final String DOMAIN_URL = "http://localhost:" + port;
	private ResponseEntity<JsonNode> response = null;
	private HttpClientErrorException clientError = null;

	// Happy path for existing course
	@Given("a user sends a POST request with new invalid course")
	public void a_user_sends_a_post_request_with_new_invalid_course(DataTable invalidCourseDatatable) {
		Map<String, String> map = invalidCourseDatatable.asMap(String.class, String.class);
		MultiValueMap<String, String> courseMultiValueMap = new LinkedMultiValueMap<>();
		courseMultiValueMap.add("id", map.get("id"));
		// Headers and body for form url encoded request
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(courseMultiValueMap, headers);		
		try {
			response = rest.postForEntity(DOMAIN_URL + "/courses", request, JsonNode.class);	
		}catch(HttpClientErrorException exp) {
			clientError = exp;
		}
		
	}

	@Then("the response status should be {int} unprocessable entity")
	public void the_response_status_code_should_be(int expectedCode) {
		assertEquals(expectedCode, clientError.getStatusCode().value());
	}

	@Then("the response content should be :")
	public void the_response_content_should_be(String expectedContent) {
		JsonNode expectedJsonNodeError = null;
		JsonNode responseJsonNodeError = null;
		try {
			expectedJsonNodeError = mapper.readTree(expectedContent);
			responseJsonNodeError = mapper.readTree(clientError.getResponseBodyAsString());
		} catch (Exception exp) {
		}
		assertEquals(expectedJsonNodeError, responseJsonNodeError);
	}

}