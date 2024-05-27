package com.tdd.api.bdd.stepsdefs.get_all;

import static org.junit.Assert.assertEquals;

import java.net.URI;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tdd.api.domain.Course;
import com.tdd.api.domain.CourseNotExistError;
import com.tdd.api.domain.InvalidArgumentException;
import com.tdd.api.infrastructure.jackson.parse_json.JsonCourseParser;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.cucumber.datatable.DataTable;

public class GetAllCoursesSteps {
	private RestTemplate rest = new RestTemplate();
	private Integer port = 3001;
	private URI uri = null;
	private ObjectMapper mapper = new ObjectMapper();
	private final String DOMAIN_URL = "http://localhost:" + port;

	
	@Given("there are some random courses")
	public void there_are_some_random_courses(DataTable dataTable){


	}

	@When("the client makes a GET to get all {string}")
	public void the_client_makes_a_GET_to_get_all(String getAllEndpoint) {
	}

	@Then("the response status code to get all should be {int} OK")
	public void the_response_status_code_for_getting_all_should_be(int statusCode) {

	}

	@Then("the content type should be after getting all should be {string}")
	public void the_content_type_after_getting_all_should_be(String expectedContentType) {
	}

	@Then("the response content once all retreived :")
	public void the_response_content_all_courses(String expectedContent) {
	}
	

}
