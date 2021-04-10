package com.maxcruz.game.domain.model

sealed class Card(val value: Int, private val suit: Suit) {
    class Two(suite: Suit): Card(1, suite)
    class Three(suite: Suit): Card(2, suite)
    class Four(suite: Suit): Card(3, suite)
    class Five(suite: Suit): Card(4, suite)
    class Six(suite: Suit): Card(5, suite)
    class Seven(suite: Suit): Card(6, suite)
    class Eight(suite: Suit): Card(7, suite)
    class Nine(suite: Suit): Card(8, suite)
    class Ten(suite: Suit): Card(9, suite)
    class Jack(suite: Suit): Card(10, suite)
    class Queen(suite: Suit): Card(11, suite)
    class King(suite: Suit): Card(12, suite)
    class Ace(suite: Suit): Card(13, suite)

    override fun toString(): String {
        return "${this::class.simpleName}-${suit::class.simpleName}"
    }
}