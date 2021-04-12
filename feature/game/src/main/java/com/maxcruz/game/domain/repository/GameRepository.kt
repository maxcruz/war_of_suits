package com.maxcruz.game.domain.repository

import com.maxcruz.data.datasource.GameDataSource
import com.maxcruz.data.datasource.PreferencesDataSource
import com.maxcruz.data.dto.CardDTO
import com.maxcruz.game.domain.model.Card
import com.maxcruz.game.domain.model.Deck
import com.maxcruz.game.domain.model.Priority
import com.maxcruz.game.domain.model.Suit
import javax.inject.Inject

class GameRepository @Inject constructor(
    private val preferencesDataSource: PreferencesDataSource,
    private val gameDataSource: GameDataSource,
) {

    fun getUserIdentifier(): String {
        return preferencesDataSource.getUserIdentifier()
            ?: throw IllegalStateException("The user identifier should be defined")
    }

    suspend fun createGame(
        sessionId: String,
        priority: Priority,
        deckFirstPlayer: Deck,
        deckSecondPlayer: Deck,
    ) {
        val priorityNames = priority.map { it.name }
        val firstDeck = deckFirstPlayer.map{ it.map() }
        val secondDeck = deckSecondPlayer.map { it.map() }
        gameDataSource.setupSessionGame(sessionId, priorityNames, firstDeck, secondDeck)
    }

    suspend fun getSecondPlayerCard(sessionId: String): Card =
        gameDataSource.getSecondPlayerCard(sessionId).map()

    suspend fun dropCards(sessionId: String) {
        gameDataSource.removeLastCardsFromPlayers(sessionId)
    }

    suspend fun getGamePriority(sessionId: String): Priority =
        gameDataSource.getGamePriority(sessionId).map { it.map() }

    suspend fun getGameCards(sessionId: String): Pair<Priority, Deck> {
        val (priority, deck) = gameDataSource.getSecondPlayerHand(sessionId)
        return priority.map { it.map() } to deck.map { it.map() }
    }

    private fun String.map(): Suit = when (this) {
        "spades" -> Suit.Spades
        "hearts" -> Suit.Hearts
        "diamonds" -> Suit.Diamonds
        "clubs" -> Suit.Clubs
        else -> throw IllegalStateException("Unexpected suit name $this")
    }
    private fun Card.map() : CardDTO = CardDTO(value, suit.name)
    private fun CardDTO.map() : Card {
        val suit = suit.map()
        return when (value) {
            1 -> Card.Two(suit)
            2 -> Card.Three(suit)
            3 -> Card.Four(suit)
            4 -> Card.Five(suit)
            5 -> Card.Six(suit)
            6 -> Card.Seven(suit)
            7 -> Card.Eight(suit)
            8 -> Card.Nine(suit)
            9 -> Card.Ten(suit)
            10 -> Card.Jack(suit)
            11 -> Card.Queen(suit)
            12 -> Card.King(suit)
            13 -> Card.Ace(suit)
            else -> throw IllegalStateException("Unexpected card value $value")
        }
    }
}