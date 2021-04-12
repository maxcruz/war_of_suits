package com.maxcruz.data.dto

data class GameDTO(
    val firstPlayer: String,
    var secondPlayer: String? = null,
    val priority: List<String> = emptyList(),
    val firstPlayerDeck: MutableList<CardDTO> = mutableListOf(),
    val secondPLayerDeck: MutableList<CardDTO> = mutableListOf(),
    var pointsFirstPlayer: Int = 0,
    var pointsSecondPlayer: Int = 0
)
