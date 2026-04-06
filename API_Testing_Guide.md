# API Testing Guide — OrangeHRM

## Part 1: How to Capture API Requests from Browser DevTools

### Step-by-Step:

1. Open Chrome and go to: `https://opensource-demo.orangehrmlive.com`
2. Press **F12** (or right-click → Inspect) to open DevTools
3. Click the **Network** tab
4. Check the box **"Preserve log"** (so requests don't disappear after redirect)
5. Filter by **"XHR"** or **"Fetch"** to see only API calls (not images/CSS)
6. Now perform an action (e.g., log in)
7. You'll see requests appear in the list

### What to look for:
- **Login request**: Look for a POST to `/web/index.php/auth/validateCredentials` or `/api/v2/auth/login`
- **Add Employee**: Look for a POST to `/api/v2/pim/employees`
- Click any request → you'll see:
  - **Headers tab**: request URL, method, response code
  - **Payload tab**: the data you sent (request body)
  - **Response tab**: what the server returned (usually JSON)

---

## Part 2: Postman Collection

Import the JSON below into Postman (File → Import → Raw Text):

```json
{
  "info": {
    "name": "OrangeHRM API Tests",
    "_postman_id": "orangehrm-api-collection",
    "description": "API test collection for OrangeHRM Demo",
    "schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json"
  },
  "variable": [
    {
      "key": "baseUrl",
      "value": "https://opensource-demo.orangehrmlive.com",
      "type": "string"
    },
    {
      "key": "token",
      "value": "",
      "type": "string",
      "description": "Auth token - auto-set by Login test"
    }
  ],
  "item": [
    {
      "name": "1. Login (Get Token)",
      "event": [
        {
          "listen": "test",
          "script": {
            "exec": [
              "// TEST 1: Check the HTTP status code",
              "pm.test('Status code is 200', function () {",
              "    pm.response.to.have.status(200);",
              "});",
              "",
              "// TEST 2: Response body contains a token",
              "pm.test('Response has auth token', function () {",
              "    var json = pm.response.json();",
              "    pm.expect(json).to.have.property('data');",
              "    pm.expect(json.data).to.have.property('token');",
              "    pm.expect(json.data.token).to.be.a('string');",
              "});",
              "",
              "// TEST 3: Token type is 'Bearer'",
              "pm.test('Token type is Bearer', function () {",
              "    var json = pm.response.json();",
              "    pm.expect(json.data.tokenType).to.equal('Bearer');",
              "});",
              "",
              "// SAVE TOKEN: Store token in collection variable for reuse",
              "var responseJson = pm.response.json();",
              "if (responseJson.data && responseJson.data.token) {",
              "    pm.collectionVariables.set('token', responseJson.data.token);",
              "    console.log('Token saved: ' + responseJson.data.token);",
              "}"
            ],
            "type": "text/javascript"
          }
        }
      ],
      "request": {
        "method": "POST",
        "header": [
          { "key": "Content-Type", "value": "application/json" },
          { "key": "Accept",       "value": "application/json" }
        ],
        "body": {
          "mode": "raw",
          "raw": "{\n    \"username\": \"Admin\",\n    \"password\": \"admin123\"\n}"
        },
        "url": {
          "raw": "{{baseUrl}}/web/index.php/api/v2/auth/login",
          "host": ["{{baseUrl}}"],
          "path": ["web", "index.php", "api", "v2", "auth", "login"]
        }
      }
    },
    {
      "name": "2. Add Employee",
      "event": [
        {
          "listen": "test",
          "script": {
            "exec": [
              "// TEST 1: Status code should be 200 or 201 (Created)",
              "pm.test('Status code is 200 or 201', function () {",
              "    pm.expect(pm.response.code).to.be.oneOf([200, 201]);",
              "});",
              "",
              "// TEST 2: Response body has employee data",
              "pm.test('Response contains employee data', function () {",
              "    var json = pm.response.json();",
              "    pm.expect(json).to.have.property('data');",
              "    pm.expect(json.data).to.have.property('empNumber');",
              "});",
              "",
              "// TEST 3: First name in response matches what we sent",
              "pm.test('Employee first name matches request', function () {",
              "    var json = pm.response.json();",
              "    pm.expect(json.data.firstName).to.equal('Postman');",
              "});",
              "",
              "// TEST 4: Last name in response matches what we sent",
              "pm.test('Employee last name matches request', function () {",
              "    var json = pm.response.json();",
              "    pm.expect(json.data.lastName).to.equal('TestUser');",
              "});"
            ],
            "type": "text/javascript"
          }
        }
      ],
      "request": {
        "method": "POST",
        "header": [
          { "key": "Content-Type",  "value": "application/json" },
          { "key": "Accept",        "value": "application/json" },
          { "key": "Authorization", "value": "Bearer {{token}}" }
        ],
        "body": {
          "mode": "raw",
          "raw": "{\n    \"firstName\": \"Postman\",\n    \"middleName\": \"\",\n    \"lastName\": \"TestUser\"\n}"
        },
        "url": {
          "raw": "{{baseUrl}}/web/index.php/api/v2/pim/employees",
          "host": ["{{baseUrl}}"],
          "path": ["web", "index.php", "api", "v2", "pim", "employees"]
        }
      }
    },
    {
      "name": "3. Get Employee List",
      "event": [
        {
          "listen": "test",
          "script": {
            "exec": [
              "pm.test('Status code is 200', function () {",
              "    pm.response.to.have.status(200);",
              "});",
              "",
              "pm.test('Response has employee data array', function () {",
              "    var json = pm.response.json();",
              "    pm.expect(json).to.have.property('data');",
              "    pm.expect(json.data).to.be.an('array');",
              "});",
              "",
              "pm.test('Response time is less than 3000ms', function () {",
              "    pm.expect(pm.response.responseTime).to.be.below(3000);",
              "});"
            ],
            "type": "text/javascript"
          }
        }
      ],
      "request": {
        "method": "GET",
        "header": [
          { "key": "Authorization", "value": "Bearer {{token}}" },
          { "key": "Accept",        "value": "application/json" }
        ],
        "url": {
          "raw": "{{baseUrl}}/web/index.php/api/v2/pim/employees?limit=10&offset=0",
          "host": ["{{baseUrl}}"],
          "path": ["web", "index.php", "api", "v2", "pim", "employees"],
          "query": [
            { "key": "limit",  "value": "10" },
            { "key": "offset", "value": "0" }
          ]
        }
      }
    }
  ]
}
```

### How to import this into Postman:
1. Open Postman
2. Click **Import** (top left)
3. Click **Raw text**
4. Paste the JSON above
5. Click **Import**

### How to run the collection in order:
1. Click on the collection name "OrangeHRM API Tests"
2. Click **Run** (Collection Runner)
3. Make sure requests are in order: Login → Add Employee → Get List
4. Click **Run OrangeHRM API Tests**

### Understanding Token Handling:
- The **Login** request sends username/password
- The server returns a **token** (a string like "abc123xyz...")
- The test script automatically saves this token: `pm.collectionVariables.set('token', ...)`
- All other requests use `{{token}}` in the Authorization header
- This is called **Bearer Token Authentication**

---

## Part 3: What to Validate

| Validation Type | What to check | Example |
|----------------|---------------|---------|
| Status Code | HTTP response code | 200 OK, 201 Created, 401 Unauthorized |
| Response Body | JSON structure | `data.token` exists and is a string |
| Authentication | Token works | Request with token returns 200, without token returns 401 |
| Response Time | Performance | Response under 3 seconds |
| Data Match | Sent = Received | firstName sent = firstName returned |
