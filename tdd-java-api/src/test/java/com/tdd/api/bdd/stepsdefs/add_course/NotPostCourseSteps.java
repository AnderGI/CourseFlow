package com.tdd.api.bdd.stepsdefs.add_course;

import static org.junit.Assert.assertEquals;

import org.springframework.http.HttpHeaders;
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

public class NotPostCourseSteps {
	private RestTemplate rest = new RestTemplate();
	private Integer port = 3001;
	private ObjectMapper mapper = new ObjectMapper();
	private JsonNode receivedInvalidCourseInJson = null;
	private HttpHeaders headers = null;
	private Integer statusCode = null;
	private String postErrorResponseBody = null;
	private final String DOMAIN_URL = "http://localhost:" + port;

	// Happy path for existing course
	@Given("a user sends a POST request with new invalid course")
	public void a_user_sends_a_post_request_with_new_invalid_course(String jsonRepresentedInvalidCourse) {
		try {
			receivedInvalidCourseInJson = mapper.readTree(jsonRepresentedInvalidCourse);
		} catch (Exception exp) {
		}

	}

	@When("course cannot be created from sent json data")
	public void course_cannot_be_created_from_sent_json_data() {
		JsonCourseParser courseParser = new JsonCourseParser();
		Course invalidCourse = null;
		try {
			invalidCourse = courseParser.fromJsonToCourse(receivedInvalidCourseInJson);
			rest.postForLocation(DOMAIN_URL + "/courses", invalidCourse);
		} catch (HttpClientErrorException exp) {
			headers = exp.getResponseHeaders();
			postErrorResponseBody = exp.getResponseBodyAsString();
			statusCode = exp.getStatusCode().value();
		} catch (InvalidArgumentException exp) {}  

	}

	@Then("the response status should be {int} unprocessable entity")
	public void the_response_status_code_should_be(int expectedCode) {
		assertEquals(expectedCode, statusCode.intValue());
	}

	@Then("content-type should be rendered as {string}")
	public void the_content_type_should_be(String expectedContent) {
		assertEquals(expectedContent, headers.getContentType().toString());
	}

	@Then("the response content should be :")
	public void the_response_content_should_be(String expectedContent) {
		JsonNode expectedJsonNodeError = null;
		JsonNode responseJsonNodeError = null;
		try {
			expectedJsonNodeError = mapper.readTree(expectedContent);
			responseJsonNodeError = mapper.readTree(postErrorResponseBody);
		} catch (Exception exp) {
		}
		assertEquals(expectedJsonNodeError, responseJsonNodeError);
	}

}