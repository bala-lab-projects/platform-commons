# Spring Boot WebMVC

Components for traditional Spring Boot WebMVC (servlet-based) applications.

## Overview

This module provides production-ready components for building Spring Boot WebMVC applications, including auto-configurations for logging, security, exception handling, async support, and a REST client with OAuth2 capabilities.

## Modules

### 📦 [starter](./starter)
Main starter module with auto-configurations:
- **Logging**: Request/response logging with MDC support
- **Security**: Method-level authorization with `@Authorization` annotation
- **Exception Handling**: Global exception handler
- **Async Support**: MDC-aware task executor

### 🌐 [rest-client](./rest-client)
REST client with OAuth2 support:
- `AbstractRestClient` base class for building REST clients
- Automatic OAuth2 token management
- Token caching and refresh
- Header propagation

### 🧪 [test-support](./test-support)
Testing utilities for WebMVC applications

## Quick Start

### Installation

Add the starter dependency to your `build.gradle.kts`:

```kotlin
dependencies {
    implementation("io.github.platform:spring-boot-webmvc-starter:1.0.0")
}
```

For REST client support, also add:

```kotlin
dependencies {
    implementation("io.github.platform:spring-boot-webmvc-rest-client:1.0.0")
}
```

### Basic Usage

```kotlin
@SpringBootApplication
class MyApplication

fun main(args: Array<String>) {
    runApplication<MyApplication>(*args)
}
```

That's it! Auto-configuration handles everything.

## Features

### 🔐 Authorization

Method-level access control using the `@Authorization` annotation:

```kotlin
@RestController
@RequestMapping("/api/admin")
class AdminController {

    @Authorization(authorizedGroups = ["admin"])
    @GetMapping("/users")
    fun getAllUsers(): List<User> {
        return userService.findAll()
    }

    @Authorization(authorizedGroups = ["admin", "manager"])
    @GetMapping("/reports")
    fun getReports(): List<Report> {
        return reportService.getReports()
    }
}
```

**How it works:**
- Reads user groups from `X-User-Groups` header
- Supports comma-separated groups: `X-User-Groups: admin,manager`
- Throws `ForbiddenException` (403) if unauthorized
- Configurable via annotation parameters

**Custom Configuration:**
```kotlin
@Authorization(
    authorizedGroups = ["admin", "superuser"],
    headerNames = ["X-User-Groups", "X-User-Roles"],  // Check multiple headers
    delimiter = ";"  // Custom delimiter
)
```

### 📝 Logging

Automatic request/response logging with MDC support:

```kotlin
@RestController
@RequestMapping("/api/users")
class UserController {

    @GetMapping("/{id}")
    fun getUser(@PathVariable id: String): User {
        // Logs automatically include:
        // - Transaction ID
        // - User ID
        // - Request method and path
        // - Response status
        log.info("Getting user: {}", id)
        return userService.getUser(id)
    }
}
```

**Log output example:**
```
[transactionId=abc-123] [userId=user@example.com] Getting user: 12345
```

### 🚨 Exception Handling

Global exception handler that converts exceptions to proper HTTP responses:

```kotlin
@Service
class UserService {
    fun getUser(id: String): User {
        return userRepository.findById(id)
            ?: throw ResourceNotFoundException("User not found: $id")
    }

    fun createUser(user: User): User {
        if (!user.email.isValidEmail()) {
            throw ValidationException("Invalid email format")
        }
        // ...
    }
}
```

**Automatic HTTP Response:**
```json
{
  "timestamp": "2025-11-02T10:30:00Z",
  "status": 404,
  "error": "Not Found",
  "message": "User not found: 12345",
  "path": "/api/users/12345"
}
```

### ⚡ Async Support

MDC-aware async task executor for maintaining context in async operations:

```kotlin
@Service
class NotificationService {

    @Async
    fun sendNotification(userId: String) {
        // MDC context (transaction ID, user ID) is automatically propagated
        log.info("Sending notification to user: {}", userId)
        // ...
    }
}
```

**Configuration:**
```yaml
spring:
  task:
    execution:
      pool:
        core-size: 10
        max-size: 20
        queue-capacity: 100
```

### 🌐 REST Client

Build type-safe REST clients with OAuth2 support:

```kotlin
@Service
class UserServiceClient(
    restTemplate: RestTemplate,
    oAuthTokenManager: OAuthTokenManager?
) : AbstractRestClient(restTemplate, oAuthTokenManager) {

    override fun getBaseUrl() = "https://api.example.com"

    fun getUser(id: String): User {
        return get("/users/$id", User::class.java).body
            ?: throw ResourceNotFoundException("User not found")
    }

    fun createUser(user: User): User {
        return post("/users", user, User::class.java).body
            ?: throw RuntimeException("Failed to create user")
    }

    fun updateUser(id: String, user: User): User {
        return put("/users/$id", user, User::class.java).body!!
    }

    fun deleteUser(id: String) {
        delete("/users/$id", Void::class.java)
    }
}
```

**Features:**
- ✅ Automatic OAuth2 token management
- ✅ Token caching and refresh (with 60-second buffer)
- ✅ Header propagation (transaction IDs, user context)
- ✅ Built-in error handling

## Configuration

### Security Configuration

```yaml
platform:
  security:
    enabled: true  # Enable/disable authorization (default: true)
```

### OAuth2 REST Client Configuration

```yaml
platform:
  rest-client:
    oauth:
      enabled: true
      token-url: https://auth.example.com/oauth/token
      client-id: ${CLIENT_ID}
      client-secret: ${CLIENT_SECRET}
      grant-type: client_credentials
      scope: read write  # Optional
```

### Logging Configuration

```yaml
logging:
  level:
    io.github.platform.commons: DEBUG
    io.github.platform.commons.filter.LoggingFilter: TRACE  # Detailed request/response logs
```

### Async Configuration

```yaml
spring:
  task:
    execution:
      pool:
        core-size: 10
        max-size: 20
        queue-capacity: 100
      thread-name-prefix: async-
```

## Advanced Usage

### Custom RestTemplate Bean

```kotlin
@Configuration
class RestClientConfig {

    @Bean
    fun restTemplate(): RestTemplate {
        val factory = HttpComponentsClientHttpRequestFactory()
        factory.setConnectTimeout(Duration.ofSeconds(5))
        factory.setConnectionRequestTimeout(Duration.ofSeconds(5))

        return RestTemplateBuilder()
            .requestFactory { factory }
            .interceptors(/* custom interceptors */)
            .build()
    }
}
```

### Custom Authorization Header Names

```kotlin
@Authorization(
    authorizedGroups = ["admin"],
    headerNames = ["X-User-Roles", "X-Groups"],  // Check multiple headers
    delimiter = "|"  // Custom delimiter
)
```

### Disable Features

```yaml
# Disable authorization
platform:
  security:
    enabled: false

# Disable OAuth for REST client
platform:
  rest-client:
    oauth:
      enabled: false
```

## Module Structure

```
spring-boot-webmvc/
├── starter/
│   ├── annotation/
│   │   └── BaseWebMvcApp.kt
│   ├── async/
│   │   └── MdcTaskDecorator.kt
│   ├── config/
│   │   ├── AsyncAutoConfiguration.kt
│   │   ├── ExceptionAutoConfiguration.kt
│   │   ├── LoggingAutoConfiguration.kt
│   │   └── SecurityAutoConfiguration.kt
│   ├── exception/
│   │   └── GlobalExceptionHandler.kt
│   ├── filter/
│   │   └── LoggingFilter.kt
│   └── security/
│       ├── Authorization.kt
│       └── AuthorizationAspect.kt
├── rest-client/
│   ├── client/
│   │   ├── AbstractRestClient.kt
│   │   └── oauth/
│   │       ├── OAuthTokenManager.kt
│   │       └── OAuthProperties.kt
│   └── config/
│       └── RestClientAutoConfiguration.kt
└── test-support/
```

## Best Practices

### Authorization

1. **Group-based access** - Use groups like "admin", "user", "manager"
2. **Least privilege** - Only grant necessary permissions
3. **Consistent naming** - Use standardized group names across services

### REST Client

1. **Timeouts** - Always configure connection and read timeouts
2. **Error handling** - Handle 4xx and 5xx responses appropriately
3. **Circuit breakers** - Consider using Resilience4j for fault tolerance

### Async Operations

1. **Don't block** - Avoid blocking operations in async methods
2. **Exception handling** - Use `@Async` with proper exception handlers
3. **Thread pool sizing** - Size pools based on workload characteristics

## Troubleshooting

### Authorization Not Working

**Problem:** `@Authorization` annotation is ignored

**Solution:**
1. Ensure `platform.security.enabled=true`
2. Verify `X-User-Groups` header is present in requests
3. Check AOP is enabled (`@EnableAspectJAutoProxy`)

### OAuth Token Not Being Added

**Problem:** REST client requests don't include OAuth token

**Solution:**
1. Enable OAuth: `platform.rest-client.oauth.enabled=true`
2. Configure token URL and credentials
3. Check `OAuthTokenManager` is being injected

### MDC Context Lost in Async

**Problem:** Transaction ID missing in async method logs

**Solution:**
- Use the auto-configured `TaskExecutor` (don't create your own)
- Annotate methods with `@Async`

## See Also

- [spring-core](../spring-core) - Core components
- [spring-boot-webflux](../spring-boot-webflux) - Reactive alternative
