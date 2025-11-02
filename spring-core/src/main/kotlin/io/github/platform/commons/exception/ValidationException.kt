package io.github.platform.commons.exception

/**
 * Exception thrown when validation fails.
 */
class ValidationException : DomainException {
    constructor(message: String) : super(ErrorCode.VALIDATION_FAILED, message)

    constructor(message: String, cause: Throwable) : super(ErrorCode.VALIDATION_FAILED, message, cause)

    constructor(resource: String, field: String, message: String) : super(
        errorCode = ErrorCode.VALIDATION_FAILED,
        message = message,
        resource = resource,
        field = field,
    )
}
