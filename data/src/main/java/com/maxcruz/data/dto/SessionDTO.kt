package com.maxcruz.data.dto

data class SessionDTO(
    val sessionId: String,
    val code: String,
    val game: GameDTO,
    val active: Boolean,
)
