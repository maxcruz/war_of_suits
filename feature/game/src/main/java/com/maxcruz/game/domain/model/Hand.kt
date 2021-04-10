package com.maxcruz.game.domain.model

typealias Priority = List<Suit>
typealias Deck = List<Card>

data class Hand(
    val player: String,
    val priority: Priority,
    val deck: Deck,
)
