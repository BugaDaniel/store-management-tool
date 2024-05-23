# Store Management API

## Overview
The Store Management API is a Spring Boot application that allows users to manage product data. It provides a RESTful interface for CRUD operations on products. 
The application includes user authentication and authorization, data validation, and error handling.

## Table of Contents
- [Getting Started](#getting-started)
- [Configuration](#configuration)
- [Project Structure](#project-structure)
- [Endpoints](#endpoints)
- [Security Configuration](#security-configuration)
- [Error Handling](#error-handling)

## Getting Started
### Prerequisites
- Java 17 or higher
- Maven 3.8.1 or higher
- Spring Boot 3.0 or higher

### Installation
- Clone the repo
- Build project using maven
- Run the project using maven (mvn spring-boot:run)
- Also you can import project in IDE, build and then run from StoreManagementApi class

## Configuration
### application.properties
- Server Port: server.port - Configures the port the application runs on.
- spring.datasource.url - URL for the H2 in-memory database for development and testing.
- spring.datasource.username: Username for H2 database and spring.datasource.password: Password for H2 database.
- spring.jpa.hibernate.ddl-auto=create - This property is generally recommended for development environments where you want a fresh schema on each application start. It's not recommended for production environments as it would erase any existing data.

## Project Structure
- **StoreManagementApi**: The main entry point of the application.
- **Controller**: Handles HTTP requests and responses.
  - **ProductController**: Manages CRUD operations for products.
- **Service**: Contains business logic.
  - **ProductService**: Provides methods to interact with the product repository.
- **Repository**: Interfaces with the database.
  - **ProductRepository**: JpaRepository for Product entities.
- **Model**: Defines the data model for the application.
  - **Product**: Entity representing a product with fields such as id, name, price, quantity, description, category, and brand.
- **SecurityConfig**: Configures security settings including user roles and permissions.
- **GlobalExceptionHandler**: Handles application-wide exceptions and provides meaningful error responses. 
- **LoadDatabase**: Preload database with 2 products

## Endpoints
### ProductController
- **GET /api/products/v1**: Fetch all products.
- **GET /api/products/v1/{id}**: Fetch a product by ID.
- **POST /api/products/v1**: Insert a new product.
- **PUT /api/products/v1/{id}**: Update an existing product.
- **PUT /api/products/v1/{id}/decrement/{newQuantity}**: Decrement product quantity.
- **PUT /api/products/v1/{id}/increment/{newQuantity}**: Increment product quantity.
- **DELETE /api/products/v1/{id}**: Delete a product by ID.

## Security Configuration
### SecurityConfig
- The application is configured with in-memory authentication for development and testing purposes. You can set up the users by modifying the SecurityConfig class.
- Uses HTTP Basic authentication.
- **Roles**:
  - **USER**: Can read product information.
  - **ADMIN**: Can read, create, update and delete product information.
- Replace userPass and adminPass with strong passwords before deploying the application to a production environment.
- Note: Ensure to replace the default credentials and configure an external authentication provider for production use to enhance security.

## Error Handling
### GlobalExceptionHandler
Handles various exceptions and returns appropriate HTTP statuses and messages:
- **404 Not Found**: 
  - Product not found.
- **409 Conflict**: 
  - Attempt to create a product with a pre-existing ID.
  - Attempt to set product quantity to a negative value.
- **400 Bad Request**: 
  - Request parameter fails validation (e.g., negative quantity).
  - Request body data fails validation (e.g., missing required fields).
- **500 Internal Server Error**: 
  - Unexpected database access error or other exceptions.


