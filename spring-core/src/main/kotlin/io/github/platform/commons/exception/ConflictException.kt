package io.github.platform.commons.exception

/**
 * Exception thrown when a resource conflict is detected.
 */
class ConflictException : DomainException {
    constructor(message: String) : super(ErrorCode.CONFLICT, message)

    constructor(message: String, cause: Throwable) : super(ErrorCode.CONFLICT, message, cause)

    constructor(resource: String, field: String, message: String) : super(
        errorCode = ErrorCode.CONFLICT,
        message = message,
        resource = resource,
        field = field,
    )
}
