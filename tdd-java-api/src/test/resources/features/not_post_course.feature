Feature: Cannot ost invalid course
A user will not be able to add a course that is not valid

    Scenario: Not add invalid course
    Given a user sends a POST request with new invalid course
    """
    {"id":{"value":"6058993f-ed6e-473b-9bbd-d9e63820e472"},"title":{"value":1234}}
    """
    When course cannot be created from sent json data
    Then the response status should be 422 unprocessable entity
    Then content-type should be rendered as "application/json"
    Then the response content should be : 
    """
    {"error":"422 Unprocessable Entity", "message":"Invalid arguments for course creation"}
    """