# NDI Project Registration & Redirect Flow

## Overview
Instead of passing redirect URLs directly in query parameters, the system now stores registered client details in the database. External projects only need to send their `clientId` during login.

## Database Table: NDI_CLIENT_DETAIL

| Field | Type | Description |
|-------|------|-------------|
| `CLIENT_ID` | VARCHAR(50) | Unique identifier for the client project |
| `CLIENT_NAME` | VARCHAR(255) | Human-readable name of the client |
| `CLIENT_REDIRECT_URL` | VARCHAR(500) | URL to redirect after successful verification |
| `CLIENT_STATUS` | VARCHAR(20) | ACTIVE or INACTIVE |
| `CREATED_AT` | TIMESTAMP | Record creation time |
| `UPDATED_AT` | TIMESTAMP | Last update time |

---

## Setup Flow for External Projects

### Step 1: Register Your Project
```bash
POST http://localhost:8080/ndi/admin/clients/register
Content-Type: application/json

{
  "clientId": "proj_nodejs_app",
  "clientName": "Node.js Application",
  "clientRedirectUrl": "http://localhost:8081/auth/callback"
}
```

**Response:**
```json
{
  "message": "Client registered successfully",
  "clientId": "proj_nodejs_app",
  "clientName": "Node.js Application"
}
```

---

## Login Flow for External Projects

### Step 1: User Clicks "Login with NDI"
They click a button that redirects to:
```
http://localhost:8080/?clientId=proj_nodejs_app
```

**Much simpler than before!** No more encoding the redirect URL in the URL.

---

### Step 2: System Validates Client
- LoginController receives `clientId`
- Queries database to verify `clientId` exists and is ACTIVE
- Returns 400 error if invalid

---

### Step 3: User Scans QR Code
- Your NDI login page displays QR code
- User scans and authenticates via NDI wallet

---

### Step 4: Automatic Redirect
After successful verification:
- System retrieves `CLIENT_REDIRECT_URL` from database
- Redirects to: `http://localhost:8081/auth/callback?threadId=...&idNumber=...&fullName=...&verified=true`

---

## Admin APIs

### Register New Client
```bash
POST http://localhost:8080/ndi/admin/clients/register
```

### Update Client
```bash
PUT http://localhost:8080/ndi/admin/clients/{clientId}
```

### Get Client Details
```bash
GET http://localhost:8080/ndi/admin/clients/{clientId}
```

### Deactivate Client
```bash
POST http://localhost:8080/ndi/admin/clients/{clientId}/deactivate
```

---

## Example Data

**Sample Database Entry:**
```
CLIENT_ID: proj_nodejs_app
CLIENT_NAME: Node.js Application
CLIENT_REDIRECT_URL: http://localhost:8081/auth/callback
CLIENT_STATUS: ACTIVE
```

**Sample Database Entry:**
```
CLIENT_ID: proj_python_app
CLIENT_NAME: Python Flask App
CLIENT_REDIRECT_URL: http://localhost:5000/ndi/callback
CLIENT_STATUS: ACTIVE
```

---

## Benefits Over Previous Approach

| Aspect | Previous | Now |
|--------|----------|-----|
| **URL** | `/?redirect=http://localhost:8081/callback` | `/?clientId=proj_app` |
| **URL Length** | Very long | Very short |
| **Security** | Redirect URL exposed in URL | Stored securely in DB |
| **Management** | Runtime only | Persistent in database |
| **Validation** | No validation | Server-side validation |
| **Multiple Projects** | Must pass URL each time | Register once, use ID always |

---

## Flow Diagram

```
External Project                NDI Service                Database
     |                              |                          |
     |--1. Click Login with NDI---->|                          |
     |   /?clientId=proj_app        |                          |
     |                              |--2. Validate clientId--->|
     |                              |<--3. Return client data--|
     |<-----4. Redirect to QR page--|                          |
     |                              |                          |
     |--5. User scans QR code------>|                          |
     |                              |--6. NDI Webhook-------->|
     |                              |   (Verification data)    |
     |                              |                          |
     |<-----7. Redirect to callback-|                          |
     |   with verified user data    |--8. Fetch redirect URL->|
     |                              |<--9. Return redirect-----|
     |
     |--10. Receives user data------
     |   (idNumber, fullName, etc)
```

