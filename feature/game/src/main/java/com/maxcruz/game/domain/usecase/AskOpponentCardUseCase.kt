package com.maxcruz.game.domain.usecase

import com.maxcruz.game.domain.model.Card
import com.maxcruz.game.domain.repository.GameRepository
import javax.inject.Inject

class AskOpponentCardUseCase @Inject constructor(
    private val repository: GameRepository,
) {

    suspend fun execute(sessionId: String): Card {
        return repository.getSecondPlayerCard(sessionId)
    }
}