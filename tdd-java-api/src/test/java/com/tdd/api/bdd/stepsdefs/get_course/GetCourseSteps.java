package com.tdd.api.bdd.stepsdefs.get_course;

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

public class GetCourseSteps {
	private RestTemplate rest = new RestTemplate();
	private ResponseEntity<JsonNode> response;
	private Integer port = 3001;
	private URI uri = null;
	private ObjectMapper mapper = new ObjectMapper();
	private final String DOMAIN_URL = "http://localhost:" + port;

	
	@Given("there is a course")
	public void there_is_a_course(DataTable dataTable) throws CourseNotExistError, InvalidArgumentException {
		Map<String, String> datatableMap = dataTable.asMap(String.class, String.class);
		String id = datatableMap.get("id");
		String title = datatableMap.get("title");
		Course course = null;
		try {
			course = Course.createFromPrimitives(id, title);

		} catch (InvalidArgumentException exp) {} 

		JsonCourseParser parser = new JsonCourseParser();
		ObjectNode formattedCourse = null;
		try {
			formattedCourse = parser.fromCourseToJson(course);
			// Add the course that we are suposed to get 
			// Tests are atomic processes, so getting something that exists means adding it first
			uri = rest.postForLocation(DOMAIN_URL + "/courses", formattedCourse);
		} catch (JsonProcessingException exp) {}

	}

	@When("the client makes a GET to {string}")
	public void the_client_makes_a_GET_to(String endpoint) {
		// Lógica para hacer la solicitud GET al endpoint
		response = rest.getForEntity(DOMAIN_URL + endpoint, JsonNode.class);
	}

	@Then("the endpoint {string} should be the same as the location of the new resource")
	public void the_endpoint_should_be_the_same_as_the_location_of_the_new_resource(String endpoint) {
		// Lógica para verificar el código de estado de la respuesta
		assertEquals(uri.toString(), endpoint);
	}

	@Then("the response status code should be {int} OK")
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
		JsonNode expectedJsonNode = null;
		JsonNode actualJsonNode = null;
	
		try {
			expectedJsonNode = mapper.readTree(expectedContent);
			actualJsonNode = mapper.readTree(response.getBody().toString());
		} catch (Exception exp) {
			exp.printStackTrace();
		}
	
		// Comparar ambos JSONs
		assertEquals(expectedJsonNode, actualJsonNode);
	}
	

}
