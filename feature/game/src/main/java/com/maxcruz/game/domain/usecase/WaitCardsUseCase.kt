package com.maxcruz.game.domain.usecase

import com.maxcruz.game.domain.model.Hand
import com.maxcruz.game.domain.repository.GameRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * This use case is intended for the second player to wait the cards
 */
class WaitCardsUseCase @Inject constructor(
    private val repository: GameRepository,
) {

    suspend fun execute(sessionId: String): Flow<Hand> = flow {
        val user = repository.getUserIdentifier()
        val pair = repository.getGameCards(sessionId)
        val hand = Hand(user, pair.first, pair.second)
        emit(hand)
    }
}