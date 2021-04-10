package com.maxcruz.game.domain.usecase

import com.maxcruz.game.domain.model.Card
import com.maxcruz.game.domain.model.Hand
import com.maxcruz.game.domain.model.Suit
import com.maxcruz.game.domain.model.Suit.*
import com.maxcruz.game.domain.repository.GameRepository
import javax.inject.Inject

/**
 * This logic to build a game should be moved to the server side in a real context
 */
class BuildGameUseCase @Inject constructor(
    private val repository: GameRepository,
) {

    suspend fun execute(sessionId: String): Hand {
        val priority = decidePriority()
        val cards = buildDeck()
        val decks = cards.dealCards()
        repository.createGame(
            sessionId = sessionId,
            priority = priority,
            deckFirstPlayer = decks.first,
            deckSecondPlayer = decks.second
        )
        return Hand(priority, decks.first)
    }

    private fun decidePriority(): List<Suit> =
        listOf(Hearts, Diamonds, Clubs, Spades).shuffled()

    private fun buildDeck(): List<Card> =
        Clubs.playSet() + Diamonds.playSet() + Spades.playSet() + Hearts.playSet()

    private fun Suit.playSet(): List<Card> =
        listOf(
            Card.Two(this),
            Card.Three(this),
            Card.Four(this),
            Card.Five(this),
            Card.Six(this),
            Card.Seven(this),
            Card.Eight(this),
            Card.Nine(this),
            Card.Ten(this),
            Card.Jack(this),
            Card.Queen(this),
            Card.King(this),
            Card.Ace(this)
        )

    private fun List<Card>.dealCards(): Pair<List<Card>, List<Card>> =
        shuffled()
            .mapIndexed { index, value -> index to value }
            .partition { it.first >= size / 2 }
            .let { (deck1, deck2) ->
                deck1.map { it.second } to deck2.map { it.second }
            }
}