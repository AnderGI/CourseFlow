Feature: Find an existing Course
  As user I want to view the details of and existing course based on its id

  Scenario: Find an existing course
    Given there is a course
      | id                                   | title                                     |
      | bebe2469-de39-4f69-bd92-842b95b94062 | "Testing introduccion y buenas practicas" |
    When the client makes a GET to "/courses/ibebe2469-de39-4f69-bd92-842b95b94062"
    Then the response status code should be 200
    And the response content :
      """
      {
        "id": {
          "value": "bebe2469-de39-4f69-bd92-842b95b94062"
        },
        "title": {
          "value": "Testing introduccion y buenas practicas"
        }
      }
      """
