Feature: Not find a non existing Course
  As user I cannot view the details of and non existing course based on its id and should get an error

    Scenario: Not find a non existing course
      Given there is no course
      When a user makes a GET request to non existing course "/courses/3158494d-f47f-4252-9525-cff22e959fd2"
      Then the response status code for non existing course should be 404
      Then the content type shoould be "application/json"
      Then the response content for non existing course :
      """
      {"error":"404 Not Found", "message":"Specified course not found"}
      """
