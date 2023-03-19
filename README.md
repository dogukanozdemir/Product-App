# Introduction
Welcome to the documentation for the Product Service API. This API is designed to allow clients to manage their products in an efficient and secure way. The API is implemented using Java Spring Boot, and utilizes a MySQL database for storing data. The API supports the following features:

A client can create new products in the service
A client can modify its own products but cannot modify others’ products
A client can delete its own products but cannot modify others’ products
A client can view all products in the service

Before a client can use the service, they must first register or log in to the system. Once authenticated, the client will receive a JWT token that will be used to authenticate all subsequent requests.

This document outlines the API endpoints, HTTP verbs, headers, responses, and tests that are used in this project.

# Authentication

In order to use the API, clients must authenticate themselves with valid credentials. There are two authentication endpoints available: api/auth/register and api/auth/login.

Clients can register themselves by sending a POST request to the `/api/auth/register` endpoint with their username and password. After a successful registration, clients must then send a POST request to the `/api/auth/login` endpoint with their registered credentials to receive a JWT token, which must be included in the Authorization header of all subsequent requests. If a client attempts to access a protected endpoint without a valid JWT token, they will receive a 403 Forbidden error response.

## Register
To register a new client, use the following endpoint:

**Endpoint: `/api/auth/register`**

**HTTP Verb: `POST`**

**Headers:**

* **Content-Type: `application/json`**

**Request body:**

```json
{
  "username": "johndoe",
  "password": "password123"
}
```

**Response:**

```json
{
  "message": "Client johndoe registered to system successfully"
}

```

After successful registration, clients need to log in to the service to generate a JWT token.

## Login

To log in to the service and generate a JWT token, use the following endpoint:

**Endpoint: `/api/auth/login`**

**HTTP Verb: `POST`**

**Headers:**

* **Content-Type: `application/json`**

**Request body:**

```json
{
  "username": "johndoe",
  "password": "password123"
}
```

**Response:**

```json
{
  "username": "johndoe",
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...<jwt token>"
}

```

# Service API Endpoints
The following API endpoints are supported by the service?

## Create a new product

**Endpoint: `/product`**

**HTTP Verb: `POST`**

**Headers:**

* *Content-Type: `application/json`*
* *Authorization: `Bearer <jwt token>`*

**Request body:**

```json
{
    "name" : "Led TV 4K 3D",
    "description" : "4K TV with 3D glasses",
    "color" : "Black",
    "brand" : "Samsung",
    "price" : 500
}
```

**Response:**

```json
{
    "id": 102,
    "clientId": 1,
    "name": "Led TV 4K 3D",
    "description": "4K TV with 3D glasses",
    "price": 500.0,
    "brand": "Samsung",
    "color": "Black"
}
```

## Modify a product

**Endpoint: `/product/{id}`**

**HTTP Verb: `PUT`**

**Headers:**

* **Content-Type: `application/json`**
* **Authorization: `Bearer <jwt token>`**
  
**Request body:**
  
```json
{
    "name" : "LED TV",
    "description" : "4K TV with 3D glasses, discounted",
    "color" : "Red",
    "brand" : "Samsung",
    "price" : 999.99
}
```

**Response: **

```json
{
    "id": 102,
    "clientId": 1,
    "name": "LED TV",
    "description": "4K TV with 3D glasses, discounted",
    "price": 999.99,
    "brand": "Samsung",
    "color": "Red"
}
```

## Delete a product
**Endpoint: `/product/{id}`**

**HTTP Verb: `DELETE`**

**Headers:**

* **Authorization: `Bearer <jwt token>`**

**Response:**
  
```json
{
    "message": "Product with id 102 has been deleted"
}
```

## View all products

**Endpoint: `/products`**

**HTTP Verb: `GET`**

**Headers:**

* **Authorization: Bearer `<jwt token>`**

**Response:**

```json 
[
    {
        "id": 1,
        "clientId": 1,
        "name": "Led TV",
        "description": "4K TV with 3D glasses",
        "price": 500.0,
        "brand": "Samsung",
        "color": "Black"
    },
    {
        "id": 2,
        "clientId": 1,
        "name": "Water gun",
        "description": "Water gun to splash your friends!",
        "price": 29.99,
        "brand": "Toys R us",
        "color": "Blue"
    }
]
```

# Installation and Prerequisites

Before you begin, make sure you can satisfy the following versions:

* Java version 17
* Spring version 3.0.4
* Apache maven 3.8.1

## Download and Build the Code

1. Clone the repository to your local machine using Git:
```console
git clone https://github.com/dogukanozdemir/Product-App.git
```
2. Navigate to the root directory of the project:

```console
cd Product-App
```

3. Build the project using Maven:

```console
mvn clean install
```

This will download all the necessary dependencies and build the project.

Open the IDE of your choice to run or edit the program. Intellij IDEA is highly recommended

# License

This project is licensed under the terms of the MIT license.





  
  
