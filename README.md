# EduFlex - Microservices Based E-Learning System

EduFlex is a modular, scalable e-learning platform built using the **Microservices architecture**. It is designed to simplify and manage the entire educational process including user registration, course management, subscription handling, payments, exam creation, and result evaluation.

## ✨ Core Services

| Service Name           | Responsibility                                        |
| ---------------------- | ----------------------------------------------------- |
| `auth-service`         | Handles login, registration, and JWT token issuing    |
| `user-service`         | Manages user data (students, instructors, admins)     |
| `course-service`       | CRUD operations for courses                           |
| `subscription-service` | Course subscription and status tracking               |
| `payment-service`      | Manages course payment operations                     |
| `exam-service`         | Creates exams linked to courses                       |
| `exam-result-service`  | Submits and evaluates exam results                    |
| `api-gateway`          | Entry point that routes requests to internal services |
| `eureka-server`        | Service discovery registry using Spring Cloud Eureka  |

## ✅ Features

* User registration and login with secure JWT-based authentication
* Admin role to approve course publishing
* Instructors can create exams for each course
* Students can register and pay for available courses
* Track subscriptions and determine course completion based on exam results
* Dynamic service discovery and internal communication
* Retry mechanism for service-to-service communication failures
* Load balancing and centralized API Gateway

## ⚙ Technologies Used

* **Java 17**
* **Spring Boot 3.3.x**
* **Spring Cloud (Eureka, Gateway)**
* **PostgreSQL**
* **Lombok**
* **RestTemplate** (with Retryable)
* **Docker & Docker Compose**
* **Maven**

## 🚀 Run the Project

To run the project using Docker Compose:

```bash
# From the root directory
docker-compose up --build
```

Or run each service individually from your IDE (e.g., IntelliJ).

## 📃 Sample Manual Test Scenarios

1. **Register a new user**

   * `POST /api/users/register` via user-service

2. **Login and get JWT**

   * `POST /api/auth/login` via auth-service

3. **Create a course**

   * `POST /api/courses` via course-service

4. **Subscribe to a course**

   * `POST /api/subscriptions` via subscription-service

5. **Make a payment for the course**

   * `POST /api/payments` via payment-service

6. **Submit exam result**

   * `POST /api/exam-results` via exam-result-service

7. **Check if student passed**

   * `GET /api/exam-results/check?userId=...&courseId=...`

8. **Complete subscription if passed**

   * `POST /api/subscriptions/complete`

9. **Service discovery validation**

   * Visit `http://localhost:8761` (Eureka dashboard)

10. **Gateway routing test**

* Try `GET http://localhost:8080/user/api/users` (routes to user-service)

## 📦 Future Improvements

* Add service-to-service security using API keys or OAuth2
* Add a notification microservice (email/sms)
* Monitoring with Prometheus & Grafana
* Centralized logging with ELK stack

---

Created with ❤️ by Ghram
