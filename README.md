## Example Test Scenarios

The sample test scenarios have been created as shown below. The values used for test scenarios are dummy data and do not belong to real individuals or entities.

### 1. Creating User Registration (Sign Up)

**Request:**

```http
POST http://localhost:8080/register
Content-Type: application/json

{
  "name": "Burak Öztürk",
  "email": "bozturk@example.com",
  "password": "5ZTpDby+",
  "phone": "+1234567890"
}
```
This request creates a new user registration. If successfully saved, it returns the created user information with HTTP status 201 (Created).


### 2. User Authentication (Sign In)

**Request:**

```http
POST http://localhost:8080/register
Content-Type: application/json

{
  "email": "bozturk@example.com",
  "password": "5ZTpDby+"
}
```

This request performs user authentication. In the case of successful authentication, you receive a JSON Web Token (JWT) with HTTP status 200 (OK).

Successful Response:

```http

{
    "jwt": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJib3p0dXJrQGV4YW1wbGUuY29tIiwiaWF0IjoxNzAyOTMyMzQxLCJleHAiOjE3MDI5MzQxNDF9.z0l66kR4o6SMjQzyvgFzEyYISVJ2kMwSqJRj5RNCEUQ",
    "expirationTime": 86400000
}
```
