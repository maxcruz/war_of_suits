package com.maxcruz.player.domain.model

/**
 * Game session
 */
data class Game(
    val sessionId: String,
    val code: String,
    val firstPlayer: Player.FirstPlayer?,
    val secondPlayer: Player.SecondPlayer?,
)
