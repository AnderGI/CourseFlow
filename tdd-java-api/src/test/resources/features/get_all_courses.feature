Feature: Find all existing Courses
  As user I want to view the details of all the existing courses

  Scenario: Find all courses
    Given there are some random courses
      | id                                   | title                                    |
      | 73db80e4-fd39-4307-854b-42c5cd182f7a | Testing en Frontend                      |
      | c7157084-3ff7-4207-a9d2-f5f030440f4f | Introducción a scala                     |
      | d7f383dd-fc9c-49ee-a3bf-5d75bbae34ac | Configuración entorno de desarollo Linux |
    When the client makes a GET to get all "/courses" 
    Then the response status code to get all should be 200 OK
    Then the content type should be after getting all should be "application/json"
    Then the response content once all retreived :
    """
    {
      "courses" : [
        {
          "id" : {
            "value" : "73db80e4-fd39-4307-854b-42c5cd182f7a"
          },
          "title" : {
            "value" : "Testing en Frontend"
          }
        },
        {
          "id" : {
            "value" : "c7157084-3ff7-4207-a9d2-f5f030440f4f"
          },
          "title" : {
            "value" : "Introducción a scala"
          }      
        },
        {
          "id" : {
            "value" : "d7f383dd-fc9c-49ee-a3bf-5d75bbae34ac"
          },
          "title" : {
            "value" : "Configuración entorno de desarollo Linux"
          }
        }
      ]
    }
    """
