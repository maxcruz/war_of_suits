package com.maxcruz.game.domain.repository

import com.maxcruz.game.domain.model.Card
import com.maxcruz.game.domain.model.Suit
import javax.inject.Inject

class GameRepository @Inject constructor(
    // Here goes data module dependency
) {

    suspend fun createGame(
        sessionId: String,
        priority: List<Suit>,
        deckFirstPlayer: List<Card>,
        deckSecondPlayer: List<Card>,
    ) {

    }

}