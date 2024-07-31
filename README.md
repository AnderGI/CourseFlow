# Courses Microservices Project

## Overview

The Courses Microservices Project is a comprehensive system designed to manage and automate the lifecycle of courses using a microservices architecture. This project leverages Spring Boot, RabbitMQ, and n8n workflows to create a robust, event-driven system that ensures seamless communication between services and automates various tasks.

## Architecture

### Microservices

The project is divided into multiple microservices, each responsible for a specific domain. The primary services include:

- **Courses Service:** Manages course creation, updates, and retrieval.

- **Users Service:** Handles user notifications and interactions related to courses.

### Event-Driven Architecture

RabbitMQ is used to implement an event-driven architecture, facilitating communication between microservices through events. This ensures that services remain decoupled and can scale independently.

### Automation with n8n

n8n workflows are employed to automate various tasks, such as sending email notifications and transforming event data. This enhances the system's efficiency and reduces manual intervention.

## Key Components

### Spring Boot

Spring Boot is the backbone of the microservices, providing a robust framework for building and deploying Java applications.

### RabbitMQ

RabbitMQ is used for message brokering, enabling asynchronous communication between services. Key features include:

- **Exchanges and Queues:** Messages are routed through exchanges to appropriate queues.

- **Event Handling:** Services listen to specific queues to process events.

### n8n Workflows

n8n is used to automate tasks such as:

- **Email Notifications:** Sending emails to users when a new course is created.

- **Data Transformation:** Parsing and transforming event data for further processing.

## Project Structure

### Directory Layout

```plaintext
Courses/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/tdd/api/
│   │   │       ├── application/
│   │   │       ├── domain/
│   │   │       ├── infrastructure/
│   │   │       └── CoursesApplication.java
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── application-dev.properties
│   │       └── application-testing.properties
│   └── test/
│       └── java/
│           └── com/tdd/api/
│               ├── infrastructure/
│               └── PostCoursesWebControllerTest.java
└── n8n-workflows/
    ├── Courses_Email_Sender.json
    └── Courses_Notification_Workflow.json
```

### Key Files

- **CoursesApplication.java:** Main entry point for the Courses service.

- **PostCoursesWebController.java:** Handles HTTP requests for course operations.

- **RabbitMQCourseCreatedConsumer.java:** Listens for course creation events and processes them.

- **NotifyUserOnCourseCreatedEventCommandHandler.java:** Handles user notifications for course creation events.

- **n8n-workflows/Courses_Email_Sender.json:** Workflow for sending email notifications.

- **n8n-workflows/Courses_Notification_Workflow.json:** Workflow for processing course notifications.

## Testing

### Unit and Integration Tests

The project includes comprehensive unit and integration tests to ensure the reliability of the services. Tests are located in the `src/test/java/com/tdd/api/` directory.

### Testing Challenges

- **Profile Issues:** Running tests can be problematic due to profile configurations. Ensure that the correct profile is active when executing tests.

- **Workflow Inconsistencies:** Inconsistencies in GPT-generated responses can lead to workflow errors. Manual verification and adjustments are often required.

## Patterns and Best Practices

### Design Patterns

- **Command Query Responsibility Segregation (CQRS):** Separates read and write operations to optimize performance and scalability.

- **Event Sourcing:** Captures all changes to the application state as a sequence of events.

- **Hexagonal Architecture:** Promotes a clean separation between the core business logic and external dependencies.

### Best Practices

- **Decoupling Services:** Ensures that services can evolve independently.

- **Automated Testing:** Maintains code quality and reliability.

- **Continuous Integration/Continuous Deployment (CI/CD):** Automates the build and deployment process.

## Known Issues

### Testing Profile Issues

Running tests can be challenging due to profile configurations. Ensure that the correct profile (`testing`, `dev`, etc.) is active to avoid conflicts.

### Workflow Inconsistencies

Inconsistencies in GPT-generated responses can lead to errors in n8n workflows. Manual verification and adjustments are necessary to ensure the workflows function correctly.

## Conclusion

The Courses Microservices Project is a robust system designed to manage courses efficiently using a microservices architecture. While there are some known issues, such as testing profile configurations and workflow inconsistencies, the project demonstrates the power of combining Spring Boot, RabbitMQ, and n8n for building scalable and automated systems. By following best practices and addressing the known issues, this project can serve as a solid foundation for further development and enhancements.
