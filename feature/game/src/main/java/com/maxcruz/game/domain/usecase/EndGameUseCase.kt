package com.maxcruz.game.domain.usecase

import com.maxcruz.game.domain.model.Result
import com.maxcruz.game.domain.repository.GameRepository
import javax.inject.Inject

class EndGameUseCase @Inject constructor(
    val gameRepository: GameRepository
) {

    suspend fun execute(sessionId: String): Result {
        val (first, second) = gameRepository.getGamePoints(sessionId)
        gameRepository.deactivateGame(sessionId)
        return when {
            first > second -> Result.Win
            first < second -> Result.Lose
            else -> Result.Draw
        }
    }
}