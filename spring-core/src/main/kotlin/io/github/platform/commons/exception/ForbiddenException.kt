package io.github.platform.commons.exception

/**
 * Exception thrown when access to a resource is forbidden.
 */
class ForbiddenException : DomainException {
    constructor(message: String) : super(ErrorCode.FORBIDDEN, message)

    constructor(message: String, cause: Throwable) : super(ErrorCode.FORBIDDEN, message, cause)

    constructor(resource: String, field: String, message: String) : super(
        errorCode = ErrorCode.FORBIDDEN,
        message = message,
        resource = resource,
        field = field,
    )
}
