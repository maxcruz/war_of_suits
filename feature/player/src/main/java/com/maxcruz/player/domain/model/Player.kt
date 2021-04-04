package com.maxcruz.player.domain.model

/**
 * A player is a user identified as first or second player.
 * The identifier is persisted in the app storage
 */
sealed class Player {

    abstract val userId: String

    data class FirstPlayer(override val userId: String): Player()
    data class SecondPlayer(override val userId: String): Player()
}
