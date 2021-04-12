package com.maxcruz.game.domain.usecase

import com.maxcruz.game.domain.model.Card
import com.maxcruz.game.domain.repository.GameRepository
import javax.inject.Inject

class ResolveRoundUseCase @Inject constructor(
    private val repository: GameRepository,
) {

    suspend fun execute(sessionId: String, firstPlayerCard: Card, secondPlayerCard: Card): Winner {
        return when {
            firstPlayerCard.value > secondPlayerCard.value -> {
                Winner.First
            }
            firstPlayerCard.value < secondPlayerCard.value -> {
                Winner.Second
            }
            else -> {
                val priority = repository.getGamePriority(sessionId)
                val firstCardIndex = priority.indexOf(firstPlayerCard.suit)
                val secondCardIndex = priority.indexOf(secondPlayerCard.suit)
                if (firstCardIndex > secondCardIndex) Winner.First else Winner.Second
            }
        }.also { repository.dropCards(sessionId) }
    }

    sealed class Winner {
        object First: Winner()
        object Second: Winner()
    }
}