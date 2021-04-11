package com.maxcruz.game.domain.model

sealed class Suit(val name: String) {
    object Spades: Suit("spades")
    object Hearts: Suit("hearts")
    object Diamonds: Suit("diamonds")
    object Clubs: Suit("clubs")
}
