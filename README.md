# StockSphere - Inventory & Billing Management System

StockSphere is a desktop-based application developed using Java Swing and MySQL. The project is designed to make basic inventory and billing management easier.

It allows users to manage products and customers, create bills, and view sales information from the database.

## Features

- User Login
- Dashboard with product and customer details
- Add, update, delete and search products
- Customer management
- Billing system
- Sales report
- Total bills and total sales calculation
- MySQL database integration

## Technologies Used

- Java
- Java Swing
- MySQL
- JDBC
- Maven

## Project Structure

```text
src/main/java/com/stocksphere
│
├── dao
│   ├── BillDAO.java
│   ├── CustomerDAO.java
│   ├── ProductDAO.java
│   └── UserDAO.java
│
├── database
│   └── DBConnection.java
│
├── model
│   ├── Bill.java
│   ├── Customer.java
│   ├── Product.java
│   └── User.java
│
├── ui
│   ├── LoginFrame.java
│   ├── DashboardFrame.java
│   ├── ProductFrame.java
│   ├── CustomerFrame.java
│   ├── BillingFrame.java
│   └── SalesReportFrame.java
│
└── Main.java
