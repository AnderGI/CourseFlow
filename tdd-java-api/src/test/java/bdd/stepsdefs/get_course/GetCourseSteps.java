package bdd.stepsdefs.get_course;

import java.util.List;
import java.util.Map;


import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.cucumber.datatable.DataTable;

public class GetCourseSteps {
   
    @Given("there is a course")
    public void there_is_a_course(DataTable dataTable) {
        List<Map<String, String>> courseData = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> course : courseData) {
            String id = course.get("id");
            String title = course.get("title");
            // Aquí puedes crear un curso con los valores id y title
            System.out.println("Curso creado con id: " + id + " y título: " + title);
        }
    }

    @When("the client makes a GET to {string}")
    public void the_client_makes_a_GET_to(String endpoint) {
        // Lógica para hacer la solicitud GET al endpoint
    }

    @Then("the response status code should be {int}")
    public void the_response_status_code_should_be(int statusCode) {
        // Lógica para verificar el código de estado de la respuesta
    }

    @Then("the response content :")
    public void the_response_content(String expectedContent) {
        // Lógica para verificar el contenido de la respuesta
    }
}
