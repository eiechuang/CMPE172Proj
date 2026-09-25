# CMPE172Proj

# Chicken Shop Ordering System

This project is a Java Spring Boot application for a chicken restaurant ordering system.

The system is designed around two main user roles: customers and staff. Customers can browse menu items, create food orders, and select pickup times. Staff members can view submitted orders, see the items included in each order, and access preparation information.

The project also includes pickup-slot scheduling so that customers can reserve pickup times without creating duplicate bookings for the same time slot.

## Main Features

- Browse restaurant menu items
- View available pickup time slots
- Create customer orders
- Support multiple items within an order
- Prevent duplicate pickup bookings for the same customer
- Allow staff to view active orders
- Store preparation instructions for menu items
- Support future order cancellation and order-status tracking

## Technology

- Java
- Spring Boot
- SQL
- JDBC
- REST APIs
- Maven

The project uses JDBC for direct SQL database access and does not use an ORM such as Hibernate or JPA.

## Project Structure

The application follows a layered architecture:

- **Controller** - receives HTTP requests
- **Service** - handles application and business logic
- **Repository** - communicates with the SQL database using JDBC
- **DTOs** - transfer data between the application and client

The general request flow is:

`Client -> Controller -> Service -> Repository -> Database`

## Database

`schema.sql` creates the database tables and constraints.

`seed.sql` inserts sample data such as menu items and pickup time slots when the application starts.
