package com.tdd.api.bdd.stepsdefs.get_all;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
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
import com.tdd.api.domain.CourseId;
import com.tdd.api.domain.exceptions.InvalidArgumentException;
import com.tdd.api.infrastructure.jackson.parse_json.JsonCourseParser;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.cucumber.datatable.DataTable;
import io.cucumber.datatable.DataTable.TableConverter;
import io.cucumber.datatable.DataTableType;
import io.cucumber.datatable.TableCellTransformer;

public class GetAllCoursesSteps {
	private RestTemplate rest = new RestTemplate();
	private Integer port = 3001;
	private URI uri = null;
	private ObjectMapper mapper = new ObjectMapper();
	private final String DOMAIN_URL = "http://localhost:" + port;
	private ResponseEntity<JsonNode> response = null;

	@Given("there are some random courses")
	// asegurarme de vacira primero repositorio
	public void there_are_some_random_courses(DataTable datatable) throws InvalidArgumentException {

		List<Course> courses = this.transformDataTableIntoCourses(datatable);
		for (Course course : courses) {
			MultiValueMap<String, String> courseFormData = new LinkedMultiValueMap<>();
			courseFormData.set("id", course.getIdValue());
			courseFormData.set("title", course.getTitleValue());
			// For posting via form url encoded data
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(
					courseFormData, headers);
			rest.postForEntity(DOMAIN_URL + "/courses", request, JsonNode.class);
		}

	}

	@When("the client makes a GET to get all {string}")
	public void the_client_makes_a_GET_to_get_all(String getAllEndpoint) {
		response = rest.getForEntity(DOMAIN_URL + "/courses", JsonNode.class);
	}

	@Then("the response status code to get all should be {int} OK")
	public void the_response_status_code_for_getting_all_should_be(int statusCode) {
		assertEquals(statusCode, response.getStatusCode().value());
	}

	@Then("the content type should be after getting all should be {string}")
	public void the_content_type_after_getting_all_should_be(String expectedContentType) {
		assertEquals(expectedContentType, response.getHeaders().getContentType().toString());
	}

	@Then("the response content once all retreived :")
	public void the_response_content_all_courses(String expectedContent) {

		JsonNode expected = null;
		try {
			expected = mapper.readTree(expectedContent);
		} catch (Exception exp) {
		}
		assertEquals(expected, response.getBody());

	}

	private List<Course> transformDataTableIntoCourses(DataTable datatable) throws InvalidArgumentException {

		List<Course> courses = new ArrayList<>();
		List<Map<String, String>> coursesMap = datatable.asMaps();

		for (Map<String, String> courseMap : coursesMap) {
			String id = courseMap.get("id");
			String title = courseMap.get("title");
			Course course = Course.createFromPrimitives(id, title);
			courses.add(course);
		}
		return courses;

	}

}
