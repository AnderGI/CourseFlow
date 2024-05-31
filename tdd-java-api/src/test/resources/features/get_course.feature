Feature: Find an existing Course
  As user I want to view the details of and existing course based on its id

  Scenario: Find an existing course
    Given there is a course
      | id    | e8a5974e-6e8e-42f8-9cd1-41c58e09b6e0    |
      | title | Testing introduccion y buenas practicas |
    When the client makes a GET to "/courses/e8a5974e-6e8e-42f8-9cd1-41c58e09b6e0"
    Then the response status code should be 200 OK
    Then the content type should be "application/json"
    Then the response content :
    """
    {
      "id" : {
        "value" : "e8a5974e-6e8e-42f8-9cd1-41c58e09b6e0"
      },
      "title" : {
        "value" : "Testing introduccion y buenas practicas"
      }
    }
    """

