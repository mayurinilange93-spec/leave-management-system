# Employee Leave Management System

A simple Spring Boot REST API for managing employees and their leave requests.
Built to be beginner-friendly — every class is small and commented.

## Tech Stack
- Java 17
- Spring Boot 3.3.4
- Spring Web (REST API)
- Spring Data JPA (database access)
- MySQL

## Project Structure
```
src/main/java/com/example/leavemanagement/
├── LeaveManagementApplication.java   # main entry point
├── model/
│   ├── Employee.java
│   ├── LeaveRequest.java
│   └── LeaveStatus.java              # PENDING, APPROVED, REJECTED
├── repository/
│   ├── EmployeeRepository.java
│   └── LeaveRequestRepository.java
├── service/
│   ├── EmployeeService.java
│   └── LeaveRequestService.java      # approve/reject business logic
└── controller/
    ├── EmployeeController.java
    └── LeaveRequestController.java
```

## Setup

### 1. Create the MySQL database
```sql
CREATE DATABASE leave_management_db;
```
(The app can also auto-create it, see `application.properties`.)

### 2. Configure your database credentials
Edit `src/main/resources/application.properties` and set your MySQL
username and password:
```properties
spring.datasource.username=root
spring.datasource.password=your_mysql_password
```

### 3. Run the app
```bash
mvn spring-boot:run
```
The app starts on **http://localhost:8080**. Tables are created
automatically the first time it runs (`ddl-auto=update`).

## API Endpoints

### Employees
| Method | URL                     | Description            |
|--------|-------------------------|-------------------------|
| GET    | /api/employees          | List all employees     |
| GET    | /api/employees/{id}     | Get one employee       |
| POST   | /api/employees          | Create an employee     |
| PUT    | /api/employees/{id}     | Update an employee     |
| DELETE | /api/employees/{id}     | Delete an employee     |

Example create request body:
```json
{
  "name": "Jane Doe",
  "email": "jane@example.com",
  "department": "Engineering"
}
```

### Leave Requests
| Method | URL                                     | Description                        |
|--------|------------------------------------------|-------------------------------------|
| GET    | /api/leave-requests                      | List all leave requests            |
| GET    | /api/leave-requests/{id}                 | Get one leave request              |
| GET    | /api/leave-requests/employee/{employeeId}| List leave requests for an employee|
| POST   | /api/leave-requests/apply/{employeeId}   | Apply for leave                    |
| PUT    | /api/leave-requests/{id}/approve         | Approve a pending request          |
| PUT    | /api/leave-requests/{id}/reject          | Reject a pending request           |
| DELETE | /api/leave-requests/{id}                 | Delete a leave request             |

Example apply-for-leave request body:
```json
{
  "startDate": "2026-09-10",
  "endDate": "2026-09-12",
  "reason": "Family trip"
}
```

## How it works
- Every employee starts with a leave balance of **20 days**.
- Applying for leave creates a request with status `PENDING` — the
  balance is untouched at this point.
- Approving a request checks that the employee has enough balance
  left, then subtracts the number of requested days.
- Rejecting a request just marks it `REJECTED` — no balance change.

## Next steps to try (great for learning)
- Add Spring Security so only managers can approve/reject leave.
- Add pagination to `GET /api/employees`.
- Add a global exception handler (`@ControllerAdvice`) for cleaner
  error responses instead of raw stack traces.
- Write unit tests for `LeaveRequestService` using JUnit + Mockito.
