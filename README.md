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


This request creates a new user registration. If successfully saved, it returns the created user information with HTTP status 201 (Created).
