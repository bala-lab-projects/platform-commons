package io.github.platform.commons.exception

/**
 * Abstract base exception for all domain-specific exceptions.
 * Provides structured error information including error code, resource, and field details.
 */
abstract class DomainException(
    val errorCode: ErrorCode,
    message: String,
    cause: Throwable? = null,
    val resource: String? = null,
    val field: String? = null,
) : RuntimeException(message, cause)
