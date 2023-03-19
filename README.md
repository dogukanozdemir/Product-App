# Introduction
This project is a Java Spring Boot web service that allows clients to create, modify, and delete their own products, as well as view all products in the service. The API supports the following features:

* Create a new product
* Modify a product (only if the client is the owner of the product)
* Delete a product (only if the client is the owner of the product)
* View all products

This document outlines the API endpoints, HTTP verbs, headers, responses, and tests that are used in this project.

# Authentication

The API uses JWT (JSON Web Tokens) for authentication. Clients need to register to the system and login to the service to generate a JWT token, which will be used to authorize their requests.

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

### Create a new product

**Endpoint: `/product`**

**HTTP Verb: `POST`**

**Headers:**

* *Content-Type: `application/json`*
* *Authorization: `Bearer <jwt token>`*

**Request body:**

```json
{
    "name" : "Led TV",
    "description" : "4K TV with 3D glasses",
    "color" : "Black",
    "brand" : "Samsung",
    "price" : 500
}
```

**Response:**

```json
{
  "id": 1,
  "name": "Product Name",
  "description": "Product Description",
  "price": 10.99,
  "ownerId": 12345
}
```
