package com.maxcruz.player.domain.model

/**
 * Player session
 */
data class Session(
    val sessionId: String,
    val code: String,
    val firstPlayer: Player.FirstPlayer?,
    val secondPlayer: Player.SecondPlayer?,
)
