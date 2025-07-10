# Employee API

A RESTful API for employee management built with Spring Boot. This service provides endpoints for creating, reading, updating, and deleting employee records.

## Overview

This project is a Spring Boot application that provides the following features:
- CRUD operations for employees
- Employee data validation
- Database persistence
- RESTful endpoints
- Swagger/OpenAPI documentation
- Docker containerization
- CI/CD with Jenkins

## Technologies

- Java 17
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Docker
- Jenkins
- Maven
- Swagger/OpenAPI

## Prerequisites

- Java 17 or higher
- Maven 3.6+
- Docker (optional)
- PostgreSQL instance or Docker container

## Project Structure

employee-api/
├── .github/ # GitHub specific configurations
│ └── workflows/ # GitHub Actions workflows
├── .mvn/ # Maven wrapper directory
├── src/
│ ├── main/
│ │ ├── java/com/employee/api/
│ │ │ ├── config/ # Configuration classes
│ │ │ │ ├── SwaggerConfig.java
│ │ │ │ └── WebConfig.java
│ │ │ ├── controller/ # REST controllers
│ │ │ │ └── EmployeeController.java
│ │ │ ├── model/ # Domain models
│ │ │ │ └── Employee.java
│ │ │ ├── repository/ # Data repositories
│ │ │ │ └── EmployeeRepository.java
│ │ │ ├── service/ # Business logic
│ │ │ │ ├── EmployeeService.java
│ │ │ │ └── EmployeeServiceImpl.java
│ │ │ ├── exception/ # Custom exceptions
│ │ │ │ ├── ErrorDetails.java
│ │ │ │ └── GlobalExceptionHandler.java
│ │ │ └── EmployeeApiApplication.java
│ │ └── resources/
│ │ ├── application.yml # Main application config
│ │ ├── application-dev.yml # Dev profile config
│ │ └── application-prod.yml # Prod profile config
│
│ └── test/
│ └── java/com/employee/api/
│ ├── controller/ # Controller tests
│ ├── service/ # Service layer tests
│ └── repository/ # Repository tests
│
├── .gitignore # Git ignore file
├── Dockerfile # Docker build file
├── Jenkinsfile # Jenkins CI/CD pipeline
├── docker-compose.yml # Docker Compose configuration
├── mvnw # Maven wrapper script (Unix)
├── mvnw.cmd # Maven wrapper script (Windows)
├── pom.xml # Project dependencies and build configuration
├── postman_collection.json # Postman API collection
└── README.md # Project documentation

## Getting Started

### Clone the Repository

bash git clone [https://github.com/gitingraulfr/employee-api.git](https://github.com/gitingraulfr/employee-api.git) cd employee-api

### Local Development Setup

1. Configure PostgreSQL database:

yaml
# application.yml configuration
spring: datasource: url: jdbc:postgresql://localhost:5432/employee_db username: your_username password: your_password

2. Build the project:
   bash mvn clean install

3. Run the application:
   bash mvn spring-boot:run

The application will start on `http://localhost:8080`

### Running with Docker

1. Build the Docker image:
bash docker build -t employee-api .

2. Run the container:
   bash docker run -p 8080:8080 employee-api

## API Documentation

### Swagger UI
Once the application is running, you can access the Swagger UI at:
[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

### Postman Collection
A Postman collection is available in the root directory: `postman_collection.json`

### API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET    | /api/v1/employees | Get all employees |
| GET    | /api/v1/employees/{id} | Get employee by ID |
| POST   | /api/v1/employees | Create new employee |
| PUT    | /api/v1/employees/{id} | Update employee |
| DELETE | /api/v1/employees/{id} | Delete employee |
| GET    | /api/v1/employees/search | Search employees by name | `name`: Query parameter - Name to search for |


## CI/CD Pipeline

The project includes a Jenkins pipeline configuration in `Jenkinsfile` that handles:
- Building the application
- Running tests
- Building Docker image
- Deploying to target environment

## Testing

Run the test suite using:
bash mvn test

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details

## Contact

Raúl Flores Rodríguez - https://github.com/gitingraulfr

Project Link: https://github.com/gitingraulfr/employee-api
