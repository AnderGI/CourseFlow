Feature: Find an existing Course
  As user I want to view the details of and existing course based on its id

  Scenario: Find an existing course
    Given there is a course
      | id    | e8a5974e-6e8e-42f8-9cd1-41c58e09b6e0    |
      | title | Testing introduccion y buenas practicas |
    When the client makes a GET to "/courses/e8a5974e-6e8e-42f8-9cd1-41c58e09b6e0"
    Then the endpoint "/courses/e8a5974e-6e8e-42f8-9cd1-41c58e09b6e0" should be the same as the location of the new resource
    Then the response status code should be 200
    Then the content type should be "application/json"
    Then the response content :
    """
    {"id":{"value":"e8a5974e-6e8e-42f8-9cd1-41c58e09b6e0"},"title":{"value":"Testing introduccion y buenas practicas"}}
    """
      #  Scenario: Not find a non existing course
      #   Given I user makes a GET request to "/courses/3158494d-f47f-4252-9525-cff22e959fd2"
      #   Then The response status should be 404
      #  Then The response content :
      #   \"\"\"
      #  {"error":"404 Not Found", "message":"Course with id 3158494d-f47f-4252-9525-cff22e959fd2 does not exist"}
      # \"\"\"
