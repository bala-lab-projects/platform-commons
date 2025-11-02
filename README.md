# Platform Commons

A comprehensive Spring Boot library providing common components, utilities, and auto-configurations for both WebMVC and WebFlux applications.

## Overview

Platform Commons is a modular library that eliminates boilerplate code and provides battle-tested components for building robust Spring Boot microservices. It supports both traditional servlet-based (WebMVC) and reactive (WebFlux) applications.

## Modules

### 🔹 [spring-core](./spring-core)
Core components shared across both WebMVC and WebFlux:
- Common exceptions and error handling
- DTOs (PageableDto, ServiceError)
- Constants (headers, MDC keys)
- Metrics collection
- Base annotations

### 🌐 [spring-boot-webmvc](./spring-boot-webmvc)
Components for traditional Spring Boot WebMVC applications:
- **starter**: Auto-configuration for logging, security, exception handling, async support
- **rest-client**: RestTemplate-based REST client with OAuth2 support
- **test-support**: Testing utilities for WebMVC applications

### ⚡ [spring-boot-webflux](./spring-boot-webflux)
Components for reactive Spring Boot WebFlux applications:
- **starter**: Auto-configuration for logging, security, exception handling
- **rest-client**: WebClient-based reactive REST client with OAuth2 support
- **test-support**: Testing utilities for WebFlux applications

## Quick Start

### Installation

Add the appropriate starter dependency to your `build.gradle.kts`:

**For WebMVC applications:**
```kotlin
dependencies {
    implementation("io.github.platform:spring-boot-webmvc-starter:1.0.0")
}
```

**For WebFlux applications:**
```kotlin
dependencies {
    implementation("io.github.platform:spring-boot-webflux-starter:1.0.0")
}
```

### Usage

Simply add the dependency - auto-configuration handles the rest!

```kotlin
@SpringBootApplication
class MyApplication

fun main(args: Array<String>) {
    runApplication<MyApplication>(*args)
}
```

## Features

### 🔐 Security
- **Authorization annotation** for method-level access control
- Header-based authorization (X-User-Groups)
- Configurable authorization aspect

### 📝 Logging
- MDC (Mapped Diagnostic Context) support
- Transaction ID propagation
- Request/Response logging filters
- Async-safe logging with MDC context propagation

### 🚨 Exception Handling
- Global exception handlers
- Standardized error responses
- Built-in exception types (ValidationException, ResourceNotFoundException, ForbiddenException, etc.)

### 📊 Metrics
- Micrometer-based metrics collection
- Auto-configuration for metrics
- Extensible metrics collector interface

### 🌐 REST Clients
- OAuth2-enabled REST clients
- Automatic token management and refresh
- Header propagation (transaction IDs, user context)
- Both sync (RestTemplate) and reactive (WebClient) implementations

### ⚙️ Configuration
- Externalized configuration support
- Sensible defaults
- Easy override via application.properties/yaml

## Configuration

### Security Configuration

```yaml
platform:
  security:
    enabled: true  # Enable/disable security features (default: true)
```

### REST Client OAuth Configuration

```yaml
platform:
  rest-client:
    oauth:
      enabled: true
      token-url: https://auth.example.com/oauth/token
      client-id: your-client-id
      client-secret: your-client-secret
      grant-type: client_credentials
      scope: read write
```

### Logging Configuration

```yaml
logging:
  level:
    io.github.platform.commons: DEBUG
```

## Examples

### Using Authorization

```kotlin
@RestController
@RequestMapping("/api/users")
class UserController {

    @Authorization(authorizedGroups = ["admin", "manager"])
    @GetMapping
    fun getUsers(): List<User> {
        // Only users in "admin" or "manager" groups can access
        return userService.findAll()
    }
}
```

### Using REST Client (WebMVC)

```kotlin
@Service
class UserServiceClient(
    restTemplate: RestTemplate,
    oAuthTokenManager: OAuthTokenManager?
) : AbstractRestClient(restTemplate, oAuthTokenManager) {

    override fun getBaseUrl() = "https://api.example.com"

    fun getUser(id: String): User {
        return get("/users/$id", User::class.java).body!!
    }
}
```

### Using REST Client (WebFlux)

```kotlin
@Service
class UserServiceClient(
    webClient: WebClient,
    baseUrl: String
) : AbstractReactiveRestClient(webClient, baseUrl) {

    fun getUser(id: String): Mono<User> {
        return get("/users/$id")
    }
}
```

### Exception Handling

```kotlin
@Service
class UserService {
    fun getUser(id: String): User {
        return userRepository.findById(id)
            ?: throw ResourceNotFoundException("User not found: $id")
    }
}
```

The global exception handler automatically converts this to a proper HTTP response:
```json
{
  "timestamp": "2025-11-02T10:30:00Z",
  "status": 404,
  "error": "Not Found",
  "message": "User not found: 123",
  "path": "/api/users/123"
}
```

## Development

### Building

```bash
# Build all modules
make build

# Or using Gradle directly
./gradlew build
```

### Testing

```bash
make test
```

### Formatting

```bash
# Format code (Spotless + removes unused imports via pre-commit)
make format

# Check formatting
make check-format
```

### Pre-commit Hooks

```bash
# Install hooks (includes Spotless and ktlint for automatic formatting)
make pre-commit-install

# Run all pre-commit checks
make pre-commit-run
```

### Publishing Locally

```bash
make publish-local
```

## Requirements

- Java 21+
- Spring Boot 3.2.0+
- Kotlin 2.2.20+
- Gradle 9.2.0+

## Contributing

1. Ensure all tests pass: `make test`
2. Format code: `make format`
3. Run pre-commit checks: `make pre-commit-run`
4. Follow conventional commit messages

## License

MIT License - see [LICENSE](./LICENSE) file for details.

## Author

**Balamurugan Elangovan**
Principal Software Engineer | Platform Engineering

[GitHub](https://github.com/bala-lab-projects) | [LinkedIn](https://www.linkedin.com/in/balamurugan-elangovan-53791985/) | mail.bala0224@gmail.com

## Support

For issues and questions, please open an issue on the GitHub repository.
