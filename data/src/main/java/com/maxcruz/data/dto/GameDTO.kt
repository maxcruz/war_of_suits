package com.maxcruz.data.dto

data class GameDTO(
    val firstPlayer: String,
    val secondPlayer: String? = null,
    val priority: List<String> = emptyList(),
    val firstPlayerDeck: MutableList<CardDTO> = mutableListOf(),
    val secondPLayerDeck: MutableList<CardDTO> = mutableListOf(),
)
