#  S4.01.Spring Boot REST API Fundamentals 🚀☕

## 🧾 Task Objectives

First hands-on experience with **Spring Boot** and **REST API** development.
The goal is to build a minimal yet functional API capable of receiving and returning **JSON** data, 
using HTTP methods and applying good practices from day one.

Throughout this exercise, the following key concepts are introduced:

 - What a REST API is and how it works.
 - Defining endpoints using `@RestController`.
 - Using HTTP **GET** and **POST** methods.
 - Receiving data via `@PathVariable`, `@RequestParam`, and `@RequestBody`.
 - JSON serialization and deserialization with Jackson.
 - Manual API testing with **Postman**.
 - Automated testing using **MockMvc**, `@WebMvcTest`, `@SpringBootTest`, and **Mockito**.
 - Packaging and running a Spring Boot application as an executable `.jar`.
 - Understanding **Inversion of Control (IoC)** and **Dependency Injection**.
 - Introduction to **layered architecture** and the _Controller–Service–Repository_ patterns.

---

## 📚 Documentation

 - [Spring Boot Reference Documentation](https://www.baeldung.com/spring-boot)
 - [Spring Web MVC](https://docs.spring.io/spring-framework/reference/web/webmvc.html)
 - [RESTful Web Services with Spring](https://spring.io/guides/gs/rest-service)
 - [Testing in Spring Boot (MockMvc, Mockito)](https://www.baeldung.com/mockito-series)

---


## 🧪 Proposed Exercises


### Level 1 — Health Check API

 - Spring Boot project setup.
 - Application port configuration.
 - Implementation of **GET /health** endpoint.
 - Returning structured JSON responses.
 - Manual testing using browser and Postman.
 - First controller test using MockMvc.
 - Packaging and executing the application as a `.jar`.


### Level 2 — In-Memory User Management

 - Creation of the `User` model.
 - Managing users using an in-memory list.
 - REST endpoints:
   - `GET /users`
   - `POST /users`
   - `GET /users/{id}`
   - `GET /users?name=`
 - UUID generation for unique identifiers.
 - Basic error handling (404).
 - Controller-level automated tests for all endpoints.

### Level 3 — Layered Architecture Refactor

 - Refactoring to a layered architecture:
   - Controller
   - Service
   - Repository
 - Introduction to the Repository pattern.
 - Use of IoC and Spring-managed Beans.
 - Conversion of controller tests into integration tests.
 - Unit testing the service layer with Mockito.
 - Implementation of business rules (unique email constraint).
 - Applying TDD for critical validations.
 
[Proposed Exercises](https://github.com/IT-Academy-Back/S4-Spring/blob/main/1-Spring_Intro/S4-01-Intro_Spring_Boot.md)

---

## 💻 Technologies Used

- Java 21
- Spring Boot 3.x
- Spring Web
- Maven
- JUnit 5
- Mockito
- Postman
- Git & GitHub

---

## 📋 Requirements

- Java SDK 21
- IDE with Spring Boot support (IntelliJ recommended)
- Maven
- Git

Main dependencies:

 - Spring Web
 - Spring Boot Test (JUnit, MockMvc, Mockito)

---

## 🚀 Installation

1. Clone the repository:
    ```
    git clone https://github.com/andyrouge81/S1.09-SpringBoot-UserApi.git
    ```
2. Open the project with your preferred IDE.
3. Configure the application port in application.properties:
    ```
   server.port=9000 
   ```

4. Run the application from the IDE or using Maven.

---

## ▶️ Execution

1. Run in development mode:
    ```
   mvn spring-boot:run
   ```

2. Package the application:
    ```
   mvn clean package
   ```
   
3. Run the generated `.jar` file:

    ```
   java -jar target/userapi-0.0.1-SNAPSHOT.jar
   ```
   
4. The API will be available at:
    ```
   http://localhost:9000
   ```
   
---

## 🌐 Deployment
This is an educational, practice-based project and is not intended for production use.
However, _Spring Boot_ produces a fully executable `.jar` with an embedded _Tomcat_ server, 
making it easy to deploy in any Java-compatible environment.

---

## 🤝 Contributing

1. Fork the repository
2. Create a new branch:
    ```
   git checkout -b feature/my-solution
   ```
   
3. Commit your changes using **Conventional Commits**:

    ```
   git commit -m "feat: add user service layer"
   ```
   
4. Push the branch:
    ```
   git push origin feature/my-solution
   ```
   
5. Open a Pull Request

---

💌 Contact
For questions, improvements, or feedback, 
feel free to open an Issue or reach out via the [GitHub profile](https://github.com/andyrouge81).