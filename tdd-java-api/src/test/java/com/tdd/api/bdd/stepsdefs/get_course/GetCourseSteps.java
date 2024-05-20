package com.tdd.api.bdd.stepsdefs.get_course;

import static org.junit.Assert.assertEquals;

import java.net.URI;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import com.tdd.api.bdd.CucumberSpringContextConfiguration;
import com.tdd.api.domain.Course;
import com.tdd.api.domain.CourseNotExistError;
import com.tdd.api.domain.InvalidArgumentException;

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
			// añadir el curso ya existente
			rest.postForLocation("http://localhost:" + port + "/courses", course);
		} catch (InvalidArgumentException exp) {
			System.out.println("ERROR");
		}
		
		
	}

	@When("the client makes a GET to {string}")
	public void the_client_makes_a_GET_to(String endpoint) {
		// Lógica para hacer la solicitud GET al endpoint
		response = rest.getForEntity("http://localhost:" + port + endpoint, String.class);
	}

	@Then("the response status code should be {int}")
	public void the_response_status_code_should_be(int statusCode) {
		// Lógica para verificar el código de estado de la respuesta
		assertEquals(statusCode, 200);
	}

	@Then("the response content :")
	public void the_response_content(String expectedContent) {
		assertEquals(response.getBody().equalsIgnoreCase(expectedContent), true);
	}

}
