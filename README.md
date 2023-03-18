# Introduction
This project is a Java Spring Boot web service that allows clients to create, modify, and delete their own products, as well as view all products in the service. The API supports the following features:

* Create a new product
* Modify a product (only if the client is the owner of the product)
* Delete a product (only if the client is the owner of the product)
* View all products

This document outlines the API endpoints, HTTP verbs, headers, responses, and tests that are used in this project.

# API Endpoints
The following API endpoints are supported by the service:

### Create a new product

**Endpoint: `/product`**

**HTTP Verb: `POST`**

**Headers:**

* *`Content-Type: application/json`*
* *`Authorization: Bearer <jwt token>`*

**Request body:**

```json
{
  "name": "Product Name",
  "description": "Product Description",
  "price": 10.99
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
