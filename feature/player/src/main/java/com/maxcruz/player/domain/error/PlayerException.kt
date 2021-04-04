package com.maxcruz.player.domain.error

/**
 * Domain errors expected in the business logic
 */
class PlayerException(message: String, cause: Throwable) : Exception(message, cause)