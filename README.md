# CashCard API

## Overview

CashCard API is a RESTful service implemented using Java and Spring Boot, providing a complete set of CRUD operations for managing cash cards. The application is backed by an in-memory H2 database and uses Spring Data JPA for data persistence. It includes pre-loaded data through `schema.sql` and `data.sql` scripts, making it easy to test with tools like Postman.

The API is secured with Spring Security, configuring two default users: "lorenchess" and "isaloren". Exception handling is in place for scenarios such as cash card not found and forbidden actions on other users' cards. The application also leverages server-side validation to ensure user inputs are correct.

## Features

- Full CRUD operations for cash cards
- In-memory H2 database integration
- Pre-loaded data for immediate testing
- Integration and web layer tests included
- Spring Security for user authentication
- Exception handling for robust error management
- Spring Validation for input validation

## Getting Started

### Prerequisites

- Java JDK 11 or later
- Maven 3.2+

### Running the Application

To run the application on your local machine:

1. Clone the repository:
   ```sh
   git clone https://github.com/Lorenchess/cashcard-api.git


Testing with Postman
To test the API endpoints, you can import the Postman collection included in the repository or manually set up the requests in Postman. Ensure to include the necessary authentication headers when making requests to secured endpoints.
Security
The application uses HTTP Basic Authentication. The following users are pre-configured:
Username: lorenchess, Password: password, Roles: CARD-OWNER
Username: isaloren, Password: password, Roles: CARD-OWNER
Replace password with the actual passwords configured in your SecurityConfig.
Exception Handling
The application provides custom exception handling for the following scenarios:
CashCardNotFoundException: When a requested cash card is not found in the database.
ForbiddenActionException: When a user attempts to perform an action on a cash card they do not own.
Database Schema
The H2 database schema is automatically initialized at startup using schema.sql, and test data is populated using data.sql.  
The application extends the practice project for the Spring Learning Path that I am taking as preparation for taking the VMware Spring and Spring Boot certification. https://spring.academy/courses/building-a-rest-api-with-spring-boot
