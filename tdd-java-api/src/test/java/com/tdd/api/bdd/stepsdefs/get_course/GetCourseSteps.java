package com.tdd.api.bdd.stepsdefs.get_course;

import static org.junit.Assert.assertEquals;

import java.net.URI;

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
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tdd.api.domain.Course;
import com.tdd.api.domain.exceptions.CourseNotExistError;
import com.tdd.api.domain.exceptions.InvalidArgumentException;
import com.tdd.api.infrastructure.jackson.parse_json.JsonCourseParser;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.cucumber.datatable.DataTable;

public class GetCourseSteps {
	private RestTemplate rest = new RestTemplate();
	private Integer port = 3001;
	private URI uri = null;
	private ObjectMapper mapper = new ObjectMapper();
	private final String DOMAIN_URL = "http://localhost:" + port;
	private ResponseEntity<JsonNode> postResponse = null;
	private ResponseEntity<JsonNode> getResponse = null;

	@Given("there is a course")
	public void there_is_a_course(DataTable courseDatatable) throws CourseNotExistError, InvalidArgumentException {
		Map<String, String> map = courseDatatable.asMap(String.class, String.class);
		MultiValueMap<String, String> courseFormData = new LinkedMultiValueMap<>();
		courseFormData.set("id", map.get("id"));
		courseFormData.set("title", map.get("title"));
		// For posting via form url encoded data
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(
				courseFormData, headers);

		// Add the course that we are suposed to get
		// Tests are atomic processes, so getting something that exists means adding it
		// first

		postResponse = rest.postForEntity(DOMAIN_URL + "/courses", request, JsonNode.class);
	}

	@When("the client makes a GET to {string}")
	public void the_client_makes_a_GET_to(String endpoint) {
		// Lógica para hacer la solicitud GET al endpoint
		getResponse = rest.getForEntity(DOMAIN_URL + endpoint, JsonNode.class);
	}

	@Then("the response status code should be {int} OK")
	public void the_response_status_code_should_be(int statusCode) {
		// Lógica para verificar el código de estado de la respuesta
		assertEquals(statusCode, getResponse.getStatusCode().value());
	}

	@Then("the content type should be {string}")
	public void the_content_type_should_be(String expectedContentType) {
		// Lógica para verificar el código de estado de la respuesta
		assertEquals(expectedContentType, getResponse.getHeaders().getContentType().toString());
	}

	@Then("the response content :")
	public void the_response_content(String expectedContent) {

		JsonNode expectedJsonNode = null;
		JsonNode actualJsonNode = null;

		try {
			expectedJsonNode = mapper.readTree(expectedContent);
			actualJsonNode = mapper.readTree(getResponse.getBody().toString());
		} catch (Exception exp) {
			exp.printStackTrace();
		}

		// Comparar ambos JSONs
		assertEquals(expectedJsonNode, actualJsonNode);
	}

}