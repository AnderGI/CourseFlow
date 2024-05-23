Feature: Add a new course
A user should be able to add new courses

  Scenario: Add a valid course
    Given a user sends a POST request with new course
    """
    {"id":{"value":"6058993f-ed6e-473b-9bbd-d9e63820e472"},"title":{"value":"Event-drive architecture"}}
    """
    When course is added to database in "/courses"
    Then response status code for added course is 201 created
    Then response content is :
    """
    {"message":"New course created with id 6058993f-ed6e-473b-9bbd-d9e63820e472"}
    """
