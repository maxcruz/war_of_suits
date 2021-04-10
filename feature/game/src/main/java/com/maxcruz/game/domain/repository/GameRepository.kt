package com.maxcruz.game.domain.repository

import com.maxcruz.game.domain.model.Deck
import com.maxcruz.game.domain.model.Priority
import javax.inject.Inject

class GameRepository @Inject constructor(
    // Here goes data module dependency
) {

    val user = "USER001"

    /**
     * Retrieve the user identifier.
     */
    suspend fun getUserIdentifier(): String {
        return user
    }

    suspend fun createGame(
        sessionId: String,
        priority: Priority,
        deckFirstPlayer: Deck,
        deckSecondPlayer: Deck,
    ) {
        // This operation needs to put the Game document into the session
    }

    /**
     * Second player cards
     */
    suspend fun getGameCards(sessionId: String): Pair<Priority, Deck> {
        TODO()
    }
}