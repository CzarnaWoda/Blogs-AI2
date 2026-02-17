<div align="center">

# 📝 Blogs-AI2

**A production-grade RESTful Blog Platform API built with Spring Boot 3**

[![Java](https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://openjdk.org/projects/jdk/17/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.7-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-336791?style=for-the-badge&logo=postgresql&logoColor=white)](https://www.postgresql.org/)
[![License](https://img.shields.io/badge/License-MIT-blue?style=for-the-badge)](LICENSE)

</div>

---

## 📋 Table of Contents

- [Overview](#-overview)
- [Architecture](#-architecture)
- [Tech Stack](#-tech-stack)
- [Domain Model](#-domain-model)
- [Security & Authorization](#-security--authorization)
- [API Reference](#-api-reference)
- [Getting Started](#-getting-started)
- [Configuration](#%EF%B8%8F-configuration)
- [Project Structure](#-project-structure)

---

## 📖 Overview

**Blogs-AI2** is a fully-featured backend REST API for a multi-role blog platform. It provides complete user lifecycle management, article authoring with sections/categories, a comment system with moderation, and a granular, role-based access control model — all secured via stateless **JWT authentication using RSA asymmetric keys**.

The application exposes a fully documented **OpenAPI 3 / Swagger UI** interface and is designed to serve Angular (or any SPA) frontend clients.

### Key Capabilities

- User registration, login, and profile management
- JWT-based stateless authentication (RSA-2048)
- Role-based access control with four authority levels
- Article CRUD with pagination, filtering, likes, and view tracking
- Blog sections/categories with type-based filtering
- Comment system with soft-disable moderation
- Full Swagger / OpenAPI documentation at runtime
- Configurable CORS for multiple frontend origins

---

## 🏛 Architecture

The project follows a **layered clean architecture** approach with a clear separation between domain logic, application orchestration, and infrastructure concerns.

```
┌─────────────────────────────────────────────────────────┐
│                      API Layer                          │
│         (HTTP Controllers, Request/Response DTOs)       │
├─────────────────────────────────────────────────────────┤
│                  Application Layer                      │
│         (Mappers, Controller Impls, DTO objects)        │
├─────────────────────────────────────────────────────────┤
│                    Domain Layer                         │
│     (Entities, Repository Interfaces, Exceptions)       │
├─────────────────────────────────────────────────────────┤
│                Infrastructure Layer                     │
│  (JPA Repositories, Handlers, Security, Validators)    │
└─────────────────────────────────────────────────────────┘
```

### Handler Pattern

The infrastructure layer implements a **dedicated Handler pattern** for each discrete business operation. Each handler encapsulates a single use-case (e.g., `CreateArticleHandler`, `LikeArticleHandler`, `DisableCommentByIdHandler`), promoting high cohesion and single responsibility.

```java
@Handler(handlerType = HandlerType.CREATE, name = "create article")
public class CreateArticleHandler implements CreateHandler<Article, CreateArticleRequest> {
    @Override
    public Article execute(CreateArticleRequest dto) { ... }
}
```

This approach makes each operation independently testable, easily traceable, and decoupled from the HTTP layer.

### Repository Abstraction

Domain repositories are defined as interfaces in the domain layer and implemented in the infrastructure layer using Spring Data JPA — the domain model has zero dependency on JPA internals or the persistence layer.

---

## 🛠 Tech Stack

| Category | Technology |
|---|---|
| Language | Java 17 |
| Framework | Spring Boot 3.5.7 |
| Security | Spring Security 6, OAuth2 Resource Server, JWT (RSA) |
| Persistence | Spring Data JPA, Hibernate, PostgreSQL |
| Validation | Jakarta Bean Validation (`spring-boot-starter-validation`) |
| API Docs | SpringDoc OpenAPI 3 + Swagger UI (`springdoc-openapi-starter-webmvc-ui 2.8.5`) |
| Monitoring | Spring Actuator |
| Utilities | Lombok 1.18.42 |
| Build | Maven (Spring Boot Maven Plugin) |

---

## 🗂 Domain Model

```
┌──────────────┐       ┌──────────────┐       ┌──────────────┐
│     User     │       │   Article    │       │   Section    │
├──────────────┤       ├──────────────┤       ├──────────────┤
│ id           │──1:N──│ id           │──N:1──│ id           │
│ userName     │       │ title        │       │ title        │
│ password     │       │ content(TEXT)│       │ description  │
│ email        │       │ views        │       │ type         │
│ phone        │       │ likes        │       │ views        │
│ roles        │       │ blocked      │       │ active       │
│ enabled      │       │ createdAt    │       │ createdAt    │
│ blocked      │       │ updatedAt    │       │ updatedAt    │
│ createdAt    │       └──────┬───────┘       └──────────────┘
│ updatedAt    │              │ 1:N
└──────────────┘       ┌──────▼───────┐
                        │   Comment    │
                        ├──────────────┤
                        │ id           │
                        │ content      │
                        │ likes        │
                        │ enabled      │
                        │ createdAt    │
                        │ updatedAt    │
                        └──────────────┘
```

### Entities

**`User`** — Platform user with embedded `Email` and `Phone` value objects, a set of `UserRole`s, and block/enable lifecycle flags.

**`Article`** — Blog post authored by a `User`, belonging to a `Section`. Tracks views, likes, and supports soft-blocking. Comments are managed via aggregate root methods (`addComment`, `removeComment`).

**`Section`** — Blog category/channel with a type discriminator. Managed by ADMIN, can be activated/deactivated.

**`Comment`** — Attached to an `Article`. Supports soft-disable for moderation purposes (no hard deletes).

---

## 🔐 Security & Authorization

Authentication is **stateless JWT** using RSA-2048 asymmetric key pairs. The private key signs tokens at login; the public key verifies them on every subsequent request via Spring's OAuth2 Resource Server.

### Authority Levels

| Role | Description |
|---|---|
| `USER` | Default role assigned at registration. Can like content, post comments, view protected resources. |
| `HELPER` | Can moderate comments (soft-disable). |
| `MODERATOR` | Can create and update articles and sections. |
| `ADMIN` | Full user management, role assignment/revocation, access to admin-only endpoints. |

### JWT Flow

```
Client                              Server
  │                                    │
  ├──── POST /api/v1/auth/login ───────►│
  │     { email, password, remember }  │
  │◄─── { token, user } ──────────────┤
  │                                    │
  ├──── GET /api/v1/articles  ─────────►│
  │     Authorization: Bearer <token>  │
  │◄─── { articles page } ────────────┤
```

**RSA key configuration** (`application.properties`):
```properties
rsa.private-key=classpath:certs/private.pem
rsa.public-key=classpath:certs/public.pem
```

> ⚠️ **Important:** The PEM keys included in the repository are for **development only**. Generate new key pairs before deploying to any non-local environment.

---

## 📡 API Reference

The full interactive API documentation is available at runtime:

- **Swagger UI:** `http://localhost:8080/swagger-ui.html`
- **OpenAPI JSON:** `http://localhost:8080/api-docs`

### Authentication Endpoints — `/api/v1/auth`

| Method | Path | Auth Required | Description |
|---|---|---|---|
| `POST` | `/login` | ❌ | Authenticate with email & password, returns JWT |
| `POST` | `/register` | ❌ | Create a new user account |
| `GET` | `/authorities` | ✅ | Get roles of the currently authenticated user |
| `POST` | `/changePassword` | ✅ | Update password (requires old password verification) |
| `GET` | `/me` | ✅ | Get current user profile |

### Article Endpoints — `/api/v1/articles`

| Method | Path | Required Role | Description |
|---|---|---|---|
| `POST` | `/` | `MODERATOR` | Create a new article |
| `PUT` | `/` | `MODERATOR` | Update an existing article |
| `GET` | `/` | Public | Get paginated articles (sorted by `createdAt ASC`) |
| `GET` | `/{id}` | Public | Get article by ID |
| `GET` | `/title/{title}` | Public | Get article by exact title |
| `GET` | `/author/{authorId}` | Public | Get all articles by a specific author |
| `GET` | `/section/{sectionId}` | Public | Get all articles in a section |
| `GET` | `/count/{authorName}` | ✅ | Count articles by author username |
| `PATCH` | `/like?id={id}` | ✅ | Increment like count on an article |

### Section Endpoints — `/api/v1/sections`

| Method | Path | Required Role | Description |
|---|---|---|---|
| `POST` | `/` | `ADMIN` | Create a new section |
| `PUT` | `/` | `MODERATOR` | Update an existing section |
| `GET` | `/` | Public | Get paginated sections |
| `GET` | `/{id}` | Public | Get section by ID |
| `GET` | `/title/{title}` | Public | Get section by exact title |
| `GET` | `/type/{type}` | Public | Get sections filtered by type |

### Comment Endpoints — `/api/v1/comments`

| Method | Path | Required Role | Description |
|---|---|---|---|
| `POST` | `/` | `USER` | Post a new comment on an article |
| `GET` | `/` | Public | Get paginated comments by article ID |
| `GET` | `/{id}` | ✅ | Get comment by ID |
| `PATCH` | `/like` | ✅ | Like a comment |
| `PATCH` | `/disable/{id}` | `HELPER` | Soft-disable a comment (moderation) |

### User Management Endpoints — `/api/v1/users`

| Method | Path | Required Role | Description |
|---|---|---|---|
| `GET` | `/` | `ADMIN` | Get paginated list of all users |
| `GET` | `/{id}` | ✅ | Get user by ID |
| `GET` | `/username/{username}` | ✅ | Get user by username |
| `GET` | `/byEmail?email=` | `ADMIN` | Filter users by email pattern |
| `GET` | `/byRole?role=` | `ADMIN` | Filter users by role |
| `GET` | `/byEmailRole?role=&email=` | `ADMIN` | Filter users by role AND email |
| `PUT` | `/{id}` | ✅ | Update user profile (username, phone) |
| `PATCH` | `/role/add` | `ADMIN` | Assign a role to a user |
| `PATCH` | `/role/remove` | `ADMIN` | Remove a role from a user |

### Standard Response Envelope

All endpoints return a consistent `HttpResponse` envelope:

```json
{
  "timeStamp": "2025-12-03T10:30:00",
  "httpStatus": "OK",
  "statusCode": 200,
  "reason": "Article create request",
  "message": "Article created",
  "data": {
    "article": { ... }
  }
}
```

---

## 🚀 Getting Started

### Prerequisites

- Java 17+
- Maven 3.8+
- PostgreSQL 14+

### 1. Clone the Repository

```bash
git clone https://github.com/CzarnaWoda/Blogs-AI2.git
cd Blogs-AI2
```

### 2. Create the Database

```sql
CREATE DATABASE blogs;
```

### 3. Configure the Application

Update `src/main/resources/application.properties` with your database credentials:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/blogs
spring.datasource.username=postgres
spring.datasource.password=your_password
```

> The schema is auto-managed by Hibernate (`ddl-auto=update`). No migration scripts are needed for initial setup.

### 4. Generate RSA Keys (Recommended for non-dev environments)

```bash
# Generate private key
openssl genrsa -out src/main/resources/certs/private.pem 2048

# Extract public key
openssl rsa -in src/main/resources/certs/private.pem \
            -pubout \
            -out src/main/resources/certs/public.pem
```

### 5. Build & Run

```bash
# Run with Maven wrapper
./mvnw spring-boot:run

# Or build a JAR first
./mvnw clean package
java -jar target/Blogs-AI2-0.0.1-SNAPSHOT.jar
```

The application will start on **`http://localhost:8080`**.

### 6. Verify Installation

```bash
# Register a new user
curl -X POST http://localhost:8080/api/v1/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "johndoe",
    "password": "SecurePass123!",
    "countryCode": "+48",
    "phone": "123456789",
    "email": "john@example.com"
  }'

# Login and receive a token
curl -X POST http://localhost:8080/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "john@example.com",
    "password": "SecurePass123!",
    "remember": true
  }'
```

---

## ⚙️ Configuration

### `application.properties` — Full Reference

```properties
# ─── Application ─────────────────────────────────
spring.application.name=Blogs-AI2

# ─── Database ────────────────────────────────────
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.datasource.url=jdbc:postgresql://localhost:5432/blogs
spring.datasource.username=postgres
spring.datasource.password=admin
spring.jpa.hibernate.ddl-auto=update

# ─── RSA Keys ────────────────────────────────────
rsa.private-key=classpath:certs/private.pem
rsa.public-key=classpath:certs/public.pem

# ─── Swagger / OpenAPI ───────────────────────────
springdoc.api-docs.enabled=true
springdoc.api-docs.path=/api-docs
springdoc.swagger-ui.path=/swagger-ui.html
springdoc.swagger-ui.enabled=true
springdoc.swagger-ui.operations-sorter=method
springdoc.swagger-ui.tags-sorter=alpha

# ─── CORS / Frontend ─────────────────────────────
frontend.allowed-methods=GET,POST,PUT,DELETE,PATCH
frontend.allowed-headers=*
frontend.max-age=3600
frontend.allow-credentials=true

# Add origins as needed (supports multiple)
frontend.origin-pattern-properties[0].address=localhost
frontend.origin-pattern-properties[0].https=false
frontend.origin-pattern-properties[0].port=4200
```

### CORS Configuration

The CORS policy is driven by the `frontend.*` properties and supports multiple simultaneous origins. The `FrontendProperties` bean parses these into a `CorsConfigurationSource` at startup — useful when running multiple frontend apps (e.g., a user app on `:4200` and an admin panel on `:4201`).

---

## 📁 Project Structure

```
src/main/java/me/blackwater/blogsai2/
│
├── api/                        # Shared API contracts & utilities
│   ├── data/                   # HttpResponse envelope
│   ├── enums/                  # HandlerType enum
│   ├── handler/                # Handler interfaces (CreateHandler, etc.)
│   ├── stereotype/             # @Handler annotation
│   └── util/                   # TimeUtil
│
├── application/                # Application layer
│   ├── dto/                    # Data Transfer Objects (ArticleDto, UserDto, ...)
│   ├── exception/              # GlobalExceptionHandler (@RestControllerAdvice)
│   ├── mapper/                 # Entity ↔ DTO mappers
│   └── web/
│       ├── controller/         # Controller interfaces (OpenAPI contracts) + impls
│       └── request/            # Validated request records
│
├── domain/                     # Domain layer (pure Java, no framework deps)
│   ├── exception/              # Domain exceptions (ArticleNotFoundException, etc.)
│   ├── model/                  # JPA Entities: User, Article, Section, Comment, ...
│   └── repository/             # Repository interfaces (domain-owned)
│
└── infrastructure/             # Infrastructure layer
    ├── handler/                # Use-case handlers, organized by domain
    │   ├── article/            # CreateArticleHandler, LikeArticleHandler, ...
    │   ├── comment/            # CreateCommentHandler, DisableCommentHandler, ...
    │   ├── section/            # CreateSectionHandler, GetSectionByTypeHandler, ...
    │   └── user/               # CreateUserHandler, AddRoleUserHandler, ...
    ├── repository/             # JPA repository implementations + Spring Data repos
    ├── security/
    │   ├── config/             # SecurityConfiguration, WebConfiguration, CORS
    │   ├── filter/             # JwtAuthenticationFilter
    │   ├── properties/         # RsaKeyProperties, FrontendProperties
    │   ├── provider/           # AccountAuthenticationProvider, JwtAuthenticationProvider
    │   └── service/            # TokenService, CustomUserDetailsService
    └── util/                   # ValidationUtil (email, phone, country code)
```

---

## 🤝 Contributing

Pull requests are welcome. For major changes, please open an issue first to discuss the proposed modification.

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/your-feature`)
3. Commit your changes (`git commit -m 'feat: add your feature'`)
4. Push to the branch (`git push origin feature/your-feature`)
5. Open a Pull Request

---

## 📄 License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.
