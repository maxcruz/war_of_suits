package com.maxcruz.game.domain.model

sealed class Card(val value: Int, val suit: Suit) {
    class Two(suit: Suit): Card(1, suit)
    class Three(suit: Suit): Card(2, suit)
    class Four(suit: Suit): Card(3, suit)
    class Five(suit: Suit): Card(4, suit)
    class Six(suit: Suit): Card(5, suit)
    class Seven(suit: Suit): Card(6, suit)
    class Eight(suit: Suit): Card(7, suit)
    class Nine(suit: Suit): Card(8, suit)
    class Ten(suit: Suit): Card(9, suit)
    class Jack(suit: Suit): Card(10, suit)
    class Queen(suit: Suit): Card(11, suit)
    class King(suit: Suit): Card(12, suit)
    class Ace(suit: Suit): Card(13, suit)

    override fun toString(): String {
        return "${this::class.simpleName}-${suit::class.simpleName}"
    }
}