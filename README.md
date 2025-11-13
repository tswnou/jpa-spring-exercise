JPA / Spring Boot ORM Exercise

This project is a complete implementation of a JPA ORM exercise using Spring Boot, Spring Data JPA, and a small custom-built HTML/CSS/JavaScript UI.

It demonstrates:

Full ORM mapping between multiple related entities

Use of Spring Data JPA repositories

REST API architecture with Spring MVC

Basic frontend integration (HTML + JS)

Running against both H2 (default) and Derby/Oracle (if configured)

Features
Domain Entities

The main entity is Employee, linked with the following:

Department (Many-to-One)

Country (Many-to-One)

Address (One-to-One)

EmployeePhone (One-to-Many)

Project (Many-to-Many using ProjectAssignments join table)

Use Cases Implemented

Create reference data (Country, Department)

Create Employee

Create Project

Assign Employee to one or more Projects

Retrieve, update, and list all entities

Technologies Used

Java 17+

Spring Boot 3

Spring Data JPA

Hibernate ORM

H2 / Derby / Oracle DB (configurable)

Maven

HTML / CSS / JavaScript (Vanilla)

How to Run the Application
1. Clone the repository
git clone https://github.com/tswnou/jpa-spring-exercise.git
cd jpa-spring-exercise

2. Navigate to the Spring Boot module
cd demo

3. Run with Maven Wrapper
./mvnw spring-boot:run


(Windows)

mvnw.cmd spring-boot:run

Database Configuration
Default: H2 In-Memory Database

The app auto-configures H2 at:

jdbc:h2:mem:testdb


You can access the H2 console at:

http://localhost:8080/h2-console

Switch to Derby/Oracle

Modify your application.properties:

spring.datasource.url=jdbc:derby:memory:demo;create=true
spring.jpa.hibernate.ddl-auto=create


or Oracle:

spring.datasource.url=jdbc:oracle:thin:@localhost:1521:XE
spring.datasource.username=...
spring.datasource.password=...

Project Structure
demo/
 ├── src/main/java/com/exercise/demo/
 │     ├── entity/          # JPA entities
 │     ├── repository/      # Spring Data repos
 │     ├── service/         # Business logic
 │     ├── web/controller/  # REST Controllers
 │     └── SBexerciseApplication.java
 ├── src/main/resources/
 │     ├── static/          # HTML/CSS/JS frontend
 │     └── application.properties
 └── pom.xml

Frontend (Static UI)

Inside src/main/resources/static:

ProjectHtml.html – UI page

ProjectCss.css – styling

ProjectJS.js – frontend logic

These pages can call the Spring Boot REST endpoints.

License

This project is for educational and training purposes.

Author

Ioanna
Spring Boot / JPA ORM Student Exercise
