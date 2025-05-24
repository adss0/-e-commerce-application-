# E-Commerce Web Service Application

![Java](https://img.shields.io/badge/Java-17+-orange)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.1.0-brightgreen)
![License](https://img.shields.io/badge/License-MIT-blue)

A RESTful web service backend for an e-commerce application that manages products, shopping baskets, promotions, and order processing with simulated payment integration.  
Inspired by Clean Architecture principles and the blog post: [Clean Architecture with Spring Boot - A Good Idea?](https://medium.com/@viniciusromualdobusiness/clean-architecture-with-spring-boot-a-good-idea-d6f97e450130)

---

## Table of Contents

- [Project Overview](#project-overview)
- [Features](#features)
- [Architecture](#architecture)
- [API Endpoints](#api-endpoints)
- [Getting Started](#getting-started)
    - [Prerequisites](#prerequisites)
    - [Installation](#installation)
- [Testing](#testing)
- [Further Implementation](#further-implementation)
- [License](#license)

---

## Project Overview

This Spring Boot application implements an e-commerce backend with a focus on clean separation of concerns and extensibility. It supports:

- Product catalog management
- Shopping basket creation and modification
- Promotion code application
- Conversion of baskets into orders with credit card validation
- Comprehensive error handling and logging

---

## Features

### Core Functionality
- View all available products and product details
- Create, update, and delete shopping baskets
- Add, update, or remove products from baskets
- Apply and remove promotion codes (e.g., DISCOUNT10, DISCOUNT20)
- Validate credit card details using the Luhn algorithm and expiry date checks
- Convert baskets to orders and view order history
- Global error handling for domain and application exceptions

### Technical Features
- Clean Architecture with distinct layers: entities, use cases, infrastructure, application
- Repository pattern for data access abstraction
- Dependency injection for service and repository components
- Input validation at all API endpoints
- Simulated payment gateway integration
- Modular service interfaces allowing easy swapping of implementations

---

## Architecture

The application follows Clean Architecture principles with clear separation of concerns:
````
src/main/java/com/application/ecommerce/
├── backend/
│   ├── entities/           # Core domain models representing the main business entities: Basket, Customer, Order, Product, and Promotion
│   │   ├── exceptions/     # Domain-specific exceptions related to the entities
│   │   └── [entity]/       # Packages for each entity containing the core attributes and behavior of the domain models
│   ├── infrastructure/     # Framework and implementation details, external concerns
│   │   ├── controller/     # API endpoint controllers organized by entity (e.g., Basket, Order)
│   │   │   ├── [entity]/   # Each package contains controllers relevant to the entity
│   │   │   └── ErrorsHandler # Global error handler for API exceptions
│   │   ├── gateway/        # Data access layer implementations
│   │   │   ├── [entity]/   # Packages with repositories and database schema for each entity
│   │   │   └── DatabaseConfiguration # Configuration beans for repository switching and database setup
│   │   └── services/       # External service implementations (e.g., payment, logging) swappable via interfaces
│   │       └── [service]/  # Package for the relevant service
│   │           ├── [ServiceClass].java  # Service implementation class
│   │           └── [ServiceInterface].java  # Interface to allow swapping service implementations
├── usecases/               # Application business rules and use case implementations
│   ├── [entity]/           # Packages per entity use case (Basket, Customer, Order, Product, Promotion)
│   │   ├── exceptions/     # Use case-specific exceptions
│   │   └── usecases/       # Use case implementations handling business logic for each API endpoint
├── application/            # Application-level orchestration and startup configuration
````

## API Endpoints

### Products
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET    | `/products` | Get all available products |
| GET    | `/products/{id}` | Get specific product details |
| POST   | `/products` | Create new product (Admin function) |

### Baskets
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST   | `/baskets?customerId={id}` | Create new basket for customer |
| GET    | `/baskets/{id}?customerId={id}` | Get basket details |
| PUT    | `/baskets/{customerId}/{basketId}?productId={id}&quantity={qty}&action={add/update}` | Add/update product in basket |
| DELETE | `/baskets/{customerId}/{basketId}` | Remove basket |

### Promotion
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST   | `/baskets/{basketId}/apply-promotion?customerId={id}&code={code}` | Apply discount code |
| DELETE | `/baskets/{basketId}/promotion?customerId={id}` | Remove discount from basket |

### Orders
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST   | `/orders/create?customerId={id}&basketId={id}&cardNumber={num}&expiryDate={date}&cvv={code}` | Convert basket to order |
| GET    | `/orders` | Get all orders |
| GET    | `/orders/{id}` | Get specific order details |

## Getting Started

### Prerequisites

Java 17 or higher
Maven 3.8+

Clone the repository:
```
git clone https://github.com/adss0/-e-commerce-application-.git
cd EcommerceApplication
```
Build the Project 
```
mvn clean install
```
Run the application:
```
mvn spring-boot:run
```

## Testing

The application has been tested using a Postman collection to validate the API endpoints and workflows.

You can find the complete Postman collection here:
```
EcommerceApplication/EcommerceApplication.postman_collection
```
## Further Implementation

### 1. Authentication & Authorization
- Currently lacks user authentication
- No role-based access control
- Suggested: Implement JWT with Spring Security
### 2. Customer Management
- Missing customer CRUD operations
- No customer-specific order access control
- Suggested: Add customer relationships
### 3. Persistence Layer
- Currently uses in-memory H2
- Suggested: Add PostgreSQL support

## License 

This project is licensed under the MIT License.