package com.maxcruz.player.domain.model

data class Session(
    val sessionId: String,
    val code: String,
    val firstPlayer: String?,
    val secondPlayer: String?,
)
