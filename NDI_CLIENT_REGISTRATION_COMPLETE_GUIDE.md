# NDI Client Registration System - Complete Setup Guide

## Overview

This guide provides step-by-step instructions for setting up and using the NDI client registration system, which allows external projects to register once and then authenticate users via NDI (National Digital Identity) using only a `clientId`.

## Architecture

```
External Project (Port 8081)
         ↓
    Requests NDI Login
         ↓
NDI Service (Port 8080)
    ├─ Validates clientId in Database
    ├─ Generates QR Code
    └─ After Scan: Retrieves redirect URL from DB
         ↓
    Redirects with User Data
         ↓
External Project Callback Handler
```

## Database Schema

### ndi_client_detail Table

```sql
CREATE TABLE ndi_client_detail (
    id                    BIGINT AUTO_INCREMENT PRIMARY KEY,
    client_id            VARCHAR(50) NOT NULL UNIQUE,
    client_name          VARCHAR(255) NOT NULL,
    client_redirect_url  VARCHAR(500) NOT NULL,
    client_status        VARCHAR(20) DEFAULT 'ACTIVE',
    created_at          TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at          TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_client_id (client_id),
    INDEX idx_client_status (client_status)
);
```

**Fields:**
- `client_id`: Unique identifier for the external project (e.g., "proj_banking_app")
- `client_name`: Display name of the project (e.g., "Banking Application")
- `client_redirect_url`: URL where user is redirected after successful NDI authentication
- `client_status`: "ACTIVE" or "INACTIVE" for managing client access
- `created_at` / `updated_at`: Audit timestamps

## Prerequisites

1. **Java Development Kit (JDK) 11+**
2. **Maven** (or use included `mvnw.cmd`)
3. **MySQL Server** running on localhost:3306
4. **Spring Boot 4.1.0** (included in project)

## Setup Steps

### 1. Database Configuration

**Step 1.1: Create Database**

```bash
mysql -u root -p
CREATE DATABASE bhutan_ndi_yk DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
EXIT;
```

**Step 1.2: Update application.properties**

File: `src/main/resources/application.properties`

```properties
# MySQL Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/bhutan_ndi_yk?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
spring.jpa.properties.hibernate.format_sql=true

# Flyway Configuration
spring.flyway.enabled=true
spring.flyway.locations=classpath:db/migration
```

### 2. Build the Project

```bash
cd d:\Project_Ndi\NDI-project
.\mvnw.cmd clean package -DskipTests
```

This will:
- Compile all 21 Java source files
- Copy resources and migrations
- Create `target/NDI-Project-0.0.1-SNAPSHOT.war`

### 3. Run the Application

```bash
java -jar target/NDI-Project-0.0.1-SNAPSHOT.war
```

Or deploy to Tomcat:
```bash
copy target\NDI-Project-0.0.1-SNAPSHOT.war %CATALINA_HOME%\webapps\ROOT.war
catalina.bat run
```

The application will:
- Start on `http://localhost:8080`
- Automatically run Flyway migrations to create the `ndi_client_detail` table
- Enable CORS for ports 8081, 3000, 5173, 4200

## Client Registration

### Register a New Client

**Endpoint:** `POST /ndi/admin/clients/register`

**Request:**
```json
{
    "clientId": "proj_banking_app",
    "clientName": "Banking Application",
    "clientRedirectUrl": "http://localhost:8081/auth/callback"
}
```

**Response (Success):**
```json
{
    "message": "Client registered successfully",
    "clientId": "proj_banking_app",
    "status": "ACTIVE"
}
```

**cURL Example:**
```bash
curl -X POST http://localhost:8080/ndi/admin/clients/register \
  -H "Content-Type: application/json" \
  -d '{
    "clientId": "proj_banking_app",
    "clientName": "Banking Application",
    "clientRedirectUrl": "http://localhost:8081/auth/callback"
  }'
```

### Get Client Details

**Endpoint:** `GET /ndi/admin/clients/{clientId}`

```bash
curl http://localhost:8080/ndi/admin/clients/proj_banking_app
```

**Response:**
```json
{
    "id": 1,
    "clientId": "proj_banking_app",
    "clientName": "Banking Application",
    "clientRedirectUrl": "http://localhost:8081/auth/callback",
    "clientStatus": "ACTIVE",
    "createdAt": "2026-08-17T12:00:00",
    "updatedAt": "2026-08-17T12:00:00"
}
```

### Update Client Details

**Endpoint:** `PUT /ndi/admin/clients/{clientId}`

```bash
curl -X PUT http://localhost:8080/ndi/admin/clients/proj_banking_app \
  -H "Content-Type: application/json" \
  -d '{
    "clientName": "Banking Application V2",
    "clientRedirectUrl": "http://localhost:8082/auth/callback"
  }'
```

### Deactivate Client

**Endpoint:** `POST /ndi/admin/clients/{clientId}/deactivate`

```bash
curl -X POST http://localhost:8080/ndi/admin/clients/proj_banking_app/deactivate
```

This prevents the client from using NDI authentication without deleting the record.

## Client Authentication Flow

### Step 1: Initiate Login (External Project)

**Method 1: Redirect to NDI Service**

```html
<!-- External project button -->
<a href="http://localhost:8080/?clientId=proj_banking_app" target="_blank">
  <button>Login with NDI</button>
</a>
```

**Method 2: Fetch QR Code via API**

```bash
curl http://localhost:8080/ndi/api/login-request
```

Response:
```json
{
    "qrUrl": "https://demo-client.bhutanndi.com/...",
    "deepLinkUrl": "bhutanndi://...",
    "threadId": "550e8400-e29b-41d4-a716-446655440000"
}
```

### Step 2: QR Code Display

The NDI service displays a QR code at `http://localhost:8080/?clientId=proj_banking_app`

Users scan the QR code with their NDI mobile app.

### Step 3: NDI Verification

The NDI mobile app communicates with the NDI backend to verify the user's credentials.

### Step 4: Webhook Notification

The NDI backend sends verification result to:
```
https://capacity-blemish-fondly.ngrok-free.dev/webhook
```

The NDI service receives:
```json
{
    "threadId": "550e8400-e29b-41d4-a716-446655440000",
    "idNumber": "12345678901",
    "fullName": "John Doe",
    "verified": true
}
```

### Step 5: Redirect to Client

The user is automatically redirected to their registered callback URL:

```
http://localhost:8081/auth/callback?
  threadId=550e8400-e29b-41d4-a716-446655440000&
  idNumber=12345678901&
  fullName=John%20Doe&
  verified=true
```

The external project's callback handler receives the user data and completes authentication.

## Implementation for External Projects

### Sample Node.js Callback Handler

```javascript
const express = require('express');
const app = express();

app.get('/auth/callback', (req, res) => {
    const { threadId, idNumber, fullName, verified } = req.query;
    
    if (verified === 'true') {
        // Create session for user
        req.session.user = {
            id: idNumber,
            name: fullName,
            threadId: threadId
        };
        
        res.redirect('/dashboard');
    } else {
        res.redirect('/login?error=verification_failed');
    }
});

app.listen(8081, () => {
    console.log('External project running on port 8081');
});
```

### Sample React Login Button

```jsx
import React from 'react';

function LoginButton() {
    const handleNdiLogin = () => {
        const ndiAuthUrl = 'http://localhost:8080/?clientId=proj_banking_app';
        window.location.href = ndiAuthUrl;
    };
    
    return (
        <button onClick={handleNdiLogin}>
            Login with NDI
        </button>
    );
}

export default LoginButton;
```

### Sample Python Callback Handler

```python
from flask import Flask, request, session, redirect

app = Flask(__name__)
app.secret_key = 'your-secret-key'

@app.route('/auth/callback', methods=['GET'])
def callback():
    thread_id = request.args.get('threadId')
    id_number = request.args.get('idNumber')
    full_name = request.args.get('fullName')
    verified = request.args.get('verified')
    
    if verified == 'true':
        session['user'] = {
            'id': id_number,
            'name': full_name,
            'thread_id': thread_id
        }
        return redirect('/dashboard')
    else:
        return redirect('/login?error=verification_failed')

if __name__ == '__main__':
    app.run(port=8081, debug=True)
```

## Configuration Files

### Spring Boot Configuration Class

Location: `src/main/java/Bhutan/NDI/Project/config/NdiConfig.java`

Enables CORS for external projects and configures Spring MVC settings:

```java
@Configuration
public class NdiConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/ndi/**")
                    .allowedOrigins(
                        "http://localhost:8081",
                        "http://localhost:3000",
                        "http://localhost:5173",
                        "http://localhost:4200"
                    )
                    .allowedMethods("GET", "POST", "PUT", "DELETE")
                    .allowCredentials(true)
                    .maxAge(3600);
            }
        };
    }
}
```

## Core Components

### Controllers

1. **LoginController** (`src/main/java/Bhutan/NDI/Project/controller/LoginController.java`)
   - Handles `GET /?clientId=xxxx`
   - Validates clientId against database
   - Displays NDI QR code page

2. **NdiLoginSuccessController** (`src/main/java/Bhutan/NDI/Project/controller/NdiLoginSuccessController.java`)
   - Handles redirect after NDI verification
   - Looks up clientRedirectUrl from database
   - Redirects external project with user data

3. **NdiClientAdminController** (`src/main/java/Bhutan/NDI/Project/controller/NdiClientAdminController.java`)
   - Manages client registration (CRUD operations)
   - Admin API for managing external projects

### Services

1. **NdiClientService** (`src/main/java/Bhutan/NDI/Project/services/NdiClientService.java`)
   - Business logic for client management
   - Database queries and validation
   - Methods: getActiveClient(), getClientRedirectUrl(), createClient(), updateClient(), deactivateClient()

2. **NdiService** (`src/main/java/Bhutan/NDI/Project/services/NdiService.java`)
   - NDI API integration
   - Proof request creation
   - Webhook subscription/unsubscription

3. **NdiVerificationStore** (`src/main/java/Bhutan/NDI/Project/services/NdiVerificationStore.java`)
   - In-memory store for temporary verification data
   - Maps threadId to clientId and user verification results

### Repository

**NdiClientDetailRepository** (`src/main/java/Bhutan/NDI/Project/repository/NdiClientDetailRepository.java`)

Spring Data JPA repository for database operations:
- `findByClientId(String clientId)`: Retrieve specific client
- `findByClientIdAndClientStatus(String clientId, String status)`: Get active client only

## Deployment Guide

### Local Development

```bash
# 1. Start MySQL Server
# 2. Build project
.\mvnw.cmd clean package -DskipTests

# 3. Run with embedded Tomcat
java -jar target/NDI-Project-0.0.1-SNAPSHOT.war
```

### Production Deployment

1. **Use external Tomcat/Servlet Container**
2. **Configure external database connection**
3. **Set appropriate NDI API endpoints**
4. **Configure webhook ngrok URL to actual production URL**
5. **Enable HTTPS/SSL**
6. **Add authentication to admin APIs** (`/ndi/admin/*`)
7. **Enable rate limiting and request validation**

## Security Considerations

⚠️ **Important**: The current implementation does NOT include:

1. **Admin API Authentication** - Add OAuth2/JWT tokens
2. **Request Signing** - Sign requests to prevent tampering
3. **Rate Limiting** - Add throttling to prevent abuse
4. **Input Validation** - Validate all user inputs
5. **HTTPS Enforcement** - Use SSL/TLS in production

## Example Database Entries

```sql
INSERT INTO ndi_client_detail (client_id, client_name, client_redirect_url, client_status)
VALUES 
    ('proj_banking', 'Banking Application', 'http://banking.example.com/auth/callback', 'ACTIVE'),
    ('proj_health', 'Health Application', 'http://health.example.com/auth/callback', 'ACTIVE'),
    ('proj_transport', 'Transport Application', 'http://transport.example.com/auth/callback', 'INACTIVE'),
    ('proj_education', 'Education Platform', 'https://education.example.com/ndi-callback', 'ACTIVE');
```

## Troubleshooting

### Issue: "clientId not found" Error

**Solution**: Register the client first via POST /ndi/admin/clients/register

### Issue: Database Connection Error

**Solution**: 
1. Verify MySQL is running
2. Check database name in connection URL
3. Verify username/password credentials
4. Check firewall allows port 3306

### Issue: Redirect URL Returns 404

**Solution**:
1. Verify clientRedirectUrl in database is correct
2. Ensure external project is running on correct port
3. Check CORS configuration allows the origin

### Issue: QR Code Not Displaying

**Solution**:
1. Verify NDI API credentials are correct
2. Check ngrok URL is active and correct
3. Verify network connectivity to NDI staging environment

## Support & Contact

For issues or questions:
- Email: support@ndi-project.com
- Documentation: https://docs.ndi-project.com
- API Reference: https://api.ndi-project.com/docs
