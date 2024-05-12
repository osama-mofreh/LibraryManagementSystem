# Library Management System

The Library Management System is a Java application built using Spring Boot that provides functionalities for managing books and patrons in a library.

## Table of Contents

- [Features](#features)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
- [Usage](#usage)
- [API Endpoints](#api-endpoints)
- [Testing](#testing)
- [License](#license)

## Features

- Add, update, delete, and retrieve books
- Add, update, delete, and retrieve patrons
- Borrow and return books
- Logging of method calls, exceptions, and performance metrics using AOP
- Caching frequently accessed data for improved performance
- Declarative transaction management for data integrity
- Unit testing using JUnit and Mockito

## Getting Started

### Prerequisites

To run this application, you'll need:

- Java Development Kit (JDK) version 17 or higher
- Maven

### Installation

1. Clone the repository:
git clone https://github.com/osama-mofreh/LibraryManagementSystem.git


2. Navigate to the project directory:
cd library-management-system


3. Build the project:
mvn clean install


4. Run the application:
mvn spring-boot:run


## Usage

Once the application is running, you can access the API endpoints using tools like Postman or curl.

## API Endpoints

- **GET /api/books**: Get all books
- **GET /api/books/{id}**: Get a book by ID
- **POST /api/books**: Create a new book
- **PUT /api/books/{id}**: Update an existing book
- **DELETE /api/books/{id}**: Delete a book by ID
- **GET /api/patrons**: Get all patrons
- **GET /api/patrons/{id}**: Get a patron by ID
- **POST /api/patrons**: Create a new patron
- **PUT /api/patrons/{id}**: Update an existing patron
- **DELETE /api/patrons/{id}**: Delete a patron by ID
- **POST /api/borrow/{bookId}/{patronId}**: Borrow a book
- **POST /api/return/{bookId}/{patronId}**: Return a borrowed book

## Testing

Unit tests are available in the `src/test` directory. You can run the tests using Maven:
mvn test


## License

This project is licensed under the MIT License.
