# user-items-jwt
User items microservice with JWT authentication.

## Description:
This microservice allows for the creation and retrieval of user items. Manipulation of items is only available to their owners. Available endpoints are listed below.

## Prerequisites:
- Docker

## Running instructions:
#### Running the entire app locally
- `docker compose up -d`
Will start MySQL and the microservice after checking the database healthcheck

#### or 
#### Running separately
- `docker compose up -d mysql` 
Will start MySQL only
- `mvn spring-boot:run -Dspring-boot.run.profiles=no-compose`
Will start Spring Boot alone with docker compose startup deactivated
- `./mvnw spring-boot:run -Dspring-boot.run.profiles=no-compose`
  (only if Maven not installed locally)

## Endpoints:
| Endpoint  | HTTP Method | Authorization | Description                      |
|-----------|-------------|---------------|----------------------------------|
| /login    | POST        | No            | Logging in. Returns JWT Token    |
| /register | POST        | No            | Registration. Creates a new user |
| /items    | POST        | Yes           | Creates a user's item            |
| /items    | GET         | Yes           | Gets all user's items            |

Sample requests can be found in a `UserItemsApp.postman_collection` file in this project.

## Runs on ports:
`8080` - app

`3306` - database

## Technology stack:
- Java 21
- Maven
- Spring Boot
- Spring Security
- Hibernate
- MySQL
- Liquibase
- JUnit
- Mockito
- Lombok
- Docker Compose