# SCHOLARTRACK

ScholarTrack is a full-stack scholarship management system for managing scholarship schemes, student eligibility, verifications, and disbursement tracking.

## Project Overview

Students can view available scholarship schemes, apply after automatic eligibility checks, and track application status. Verifiers review applications, approve or reject them, and process disbursement when approved.

## Features

- Student registration and profile management
- Scholarship scheme catalog with eligibility filters
- Automatic income and marks-based eligibility check
- Application submission and validation
- Verification workflow with approval/rejection remarks
- Disbursement tracking and transaction reference generation
- Swagger documentation for all major endpoints
- Modern React dashboard with responsive layout
- Sample seed data for testing the complete workflow

## Technology Stack

### Backend
- Java 17
- Spring Boot 3.3.3
- Spring Web
- Spring Data JPA
- Spring Validation
- MySQL / H2 (for easy local setup)
- Lombok
- Maven
- OpenAPI / Swagger UI

### Frontend
- React
- TypeScript
- Vite
- Axios
- React Router
- Modern CSS

## Architecture

The application is divided into two main modules:

- Backend: REST API, JPA entities, service layer, and rule enforcement
- Frontend: React dashboard screens that consume the API

## Database Design

Key tables:
- students
- schemes
- applications
- verifications
- disbursements

## Entity Relationships

- One Student has many Applications
- One Scheme has many Applications
- Many Applications belong to one Student
- Many Applications belong to one Scheme
- One Application has one Verification
- One Application has one Disbursement

## API Endpoints

### Students
- POST /api/students
- GET /api/students
- GET /api/students/{id}
- PUT /api/students/{id}
- DELETE /api/students/{id}

### Scholarships
- POST /api/schemes
- GET /api/schemes
- GET /api/schemes/{id}
- PUT /api/schemes/{id}
- DELETE /api/schemes/{id}

### Applications
- POST /api/applications
- GET /api/applications
- GET /api/applications/{id}
- GET /api/applications/student/{studentId}
- GET /api/applications/{id}/status

### Verification
- GET /api/verifications/pending
- POST /api/verifications/{applicationId}/approve
- POST /api/verifications/{applicationId}/reject

### Disbursement
- GET /api/disbursements
- GET /api/disbursements/{applicationId}
- POST /api/disbursements/{applicationId}

## Database Setup

### Option 1: Use H2 (default)
The project is configured to run with H2 memory database by default in application.properties.

### Option 2: Use MySQL
1. Start MySQL
2. Create database `scholartrack`
3. Update `spring.datasource.*` in application-mysql.properties
4. Run with:
   `mvn spring-boot:run -Dspring-boot.run.profiles=mysql`

## Backend Setup

```bash
cd backend
mvn spring-boot:run
```

## Frontend Setup

```bash
cd frontend
npm install
npm run dev
```

## How to Run

1. Start the backend:
   `cd backend && mvn spring-boot:run`
2. Start the frontend:
   `cd frontend && npm install && npm run dev`
3. Open the frontend in the browser at `http://localhost:5173`
4. Swagger UI is available at `http://localhost:8080/swagger-ui.html`

## Sample Accounts

This project uses a simple demo flow without heavy authentication:

- Student: student@scholartrack.com
- Verifier: verifier@scholartrack.com
- Admin: admin@scholartrack.com

## Testing Instructions

```bash
cd backend
mvn test
```

## Business Rules Implemented

- Application eligibility is checked automatically in the service layer.
- Ineligible applications are rejected with a clear error.
- Verification requires remarks for rejection.
- Rejected applications cannot be disbursed.
- Disbursement is blocked until verification is approved.
- Approval changes application state to `DISBURSEMENT_PENDING` and then `DISBURSED` after payment.
- Invalid transitions return meaningful HTTP errors.

## Screenshots

Add screenshots here after running the application locally.

## Demo Workflow

Student -> View scholarships -> Check eligibility -> Apply -> Pending verification -> Verifier approves/rejects -> Disbursement processing -> Disbursed
