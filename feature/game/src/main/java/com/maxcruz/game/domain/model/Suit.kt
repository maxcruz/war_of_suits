package com.maxcruz.game.domain.model

sealed class Suit {
    object Spades: Suit()
    object Hearts: Suit()
    object Diamonds: Suit()
    object Clubs: Suit()
}
