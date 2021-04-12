package com.maxcruz.game.domain.usecase

import com.maxcruz.game.domain.model.Card
import com.maxcruz.game.domain.repository.GameRepository
import javax.inject.Inject

class ResolveRoundUseCase @Inject constructor(
    private val repository: GameRepository,
) {

    suspend fun execute(sessionId: String, playingCard: Card, opponentCard: Card): Pair<Int, Int> {
        val (first, second) = when (playingCard.value) {
            opponentCard.value -> {
                val priority = repository.getGamePriority(sessionId)
                val firstCardIndex = priority.indexOf(playingCard.suit)
                val secondCardIndex = priority.indexOf(opponentCard.suit)
                firstCardIndex to secondCardIndex
            }
            else -> {
                playingCard.value to opponentCard.value
            }
        }
        val firstPoint = if (first > second) 1 else 0
        val secondPoint = if (firstPoint == 0) 1 else 0
        val points = firstPoint to secondPoint
        repository.updatePoints(sessionId, points)
        repository.dropCards(sessionId)
        return points

    }
}