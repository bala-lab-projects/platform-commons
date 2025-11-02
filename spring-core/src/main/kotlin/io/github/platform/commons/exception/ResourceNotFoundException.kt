package io.github.platform.commons.exception

/**
 * Exception thrown when a requested resource is not found.
 */
class ResourceNotFoundException : DomainException {
    constructor(message: String) : super(ErrorCode.RESOURCE_NOT_FOUND, message)

    constructor(message: String, cause: Throwable) : super(ErrorCode.RESOURCE_NOT_FOUND, message, cause)

    constructor(resource: String, field: String, message: String) : super(
        errorCode = ErrorCode.RESOURCE_NOT_FOUND,
        message = message,
        resource = resource,
        field = field,
    )
}
