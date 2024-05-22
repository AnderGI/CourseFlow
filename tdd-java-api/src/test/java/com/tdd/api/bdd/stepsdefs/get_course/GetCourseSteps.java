package com.tdd.api.bdd.stepsdefs.get_course;

import static org.junit.Assert.assertEquals;

import java.net.URI;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tdd.api.bdd.CucumberSpringContextConfiguration;
import com.tdd.api.domain.Course;
import com.tdd.api.domain.CourseNotExistError;
import com.tdd.api.domain.InvalidArgumentException;
import com.tdd.api.infrastructure.jackson.parse_json.JsonCourseParser;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.cucumber.datatable.DataTable;

@ContextConfiguration(classes = CucumberSpringContextConfiguration.class)
@SpringBootTest
public class GetCourseSteps {
	@Autowired
	private TestRestTemplate rest;
	private ResponseEntity<String> response;
	private Integer port = 3001;
	private URI uri = null;

	// Create and add the course
	@Given("there is a course")
	public void there_is_a_course(DataTable dataTable) throws CourseNotExistError, InvalidArgumentException {
		Map<String, String> datatableMap = dataTable.asMap(String.class, String.class);
		String id = datatableMap.get("id");
		String title = datatableMap.get("title");
		// OCP Violation
		Course course = null;
		try {
			course = Course.createFromPrimitives(id, title);

		} catch (InvalidArgumentException exp) {
			System.out.println("ERROR");
		}

		JsonCourseParser parser = new JsonCourseParser();
		ObjectNode formattedCourse = null;
		try {
			formattedCourse = parser.fromCourseToJson(course);
			// añadir el curso ya existente
			uri = rest.postForLocation("http://localhost:" + port + "/courses", formattedCourse);
		} catch (JsonProcessingException exp) {
		}

	}


	@When("the client makes a GET to {string}")
	public void the_client_makes_a_GET_to(String endpoint) {
		// Lógica para hacer la solicitud GET al endpoint
		response = rest.getForEntity("http://localhost:" + port + endpoint, String.class);
	}

	@Then("the endpoint {string} should be the same as the location of the new resource")
	public void the_endpoint_should_be_the_same_as_the_location_of_the_new_resource(String endpoint) {
		// Lógica para verificar el código de estado de la respuesta
		assertEquals(uri.toString(), endpoint);
	}

	@Then("the response status code should be {int}")
	public void the_response_status_code_should_be(int statusCode) {
		// Lógica para verificar el código de estado de la respuesta
		assertEquals(statusCode, 200);
	}


	@Then("the content type should be {string}")
	public void the_content_type_should_be(String expectedContentType) {
		// Lógica para verificar el código de estado de la respuesta
		assertEquals(expectedContentType, response.getHeaders().getContentType().toString());
	}

	@Then("the response content :")
	public void the_response_content(String expectedContent) {
		assertEquals(response.getBody().equalsIgnoreCase(expectedContent), true);
	}

}
