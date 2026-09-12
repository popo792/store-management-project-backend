# Relational Store Management System - Backend

A backend application mapping a complete relational store environment. It handles complex entities including customers, employees, products, and orders using a strictly defined Object-Relational Mapping (ORM) structure.

## Features
* **Entity Management:** CRUD operations for multi-layered store data.
* **In-Memory Database:** Utilizes H2 for rapid development, testing, and deployment.
* **REST API:** Exposes endpoints for frontend consumption.

## Tech Stack
* **Framework:** Java, Spring Boot
* **Database:** H2 Database (with H2 Console enabled)
* **ORM:** Spring Data JPA / Hibernate

## Setup
1. Clone the repository.
2. Build the project using Maven/Gradle.
3. Run the Spring Boot application.
4. Access the H2 console at `/h2-console` to view the relational tables.
