package com.maxcruz.game.domain.model

data class Hand(
    val priority: List<Suit>,
    val deck: List<Card>,
)
