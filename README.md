JPA / Spring Boot ORM Exercise

This project is a complete implementation of a JPA ORM exercise using Spring Boot, Spring Data JPA, and a small custom-built HTML/CSS/JavaScript UI.

It demonstrates:

Full ORM mapping between multiple related entities

Use of Spring Data JPA repositories

REST API architecture with Spring MVC

Basic frontend integration (HTML + JS)

Running against both H2 (default) and Derby/Oracle (if configured)

Features:
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

Technologies Used:

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
