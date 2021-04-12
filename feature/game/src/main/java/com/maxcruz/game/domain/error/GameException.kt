package com.maxcruz.game.domain.error

/**
 * Domain errors expected in the business logic
 */
class GameException(message: String, cause: Throwable? = null) : Exception(message, cause)