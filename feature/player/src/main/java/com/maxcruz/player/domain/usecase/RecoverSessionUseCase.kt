package com.maxcruz.player.domain.usecase

import com.maxcruz.player.domain.repository.PlayerRepository
import javax.inject.Inject

/**
 * Search an stored game session for the given user ID
 */
class RecoverSessionUseCase @Inject constructor(
    private val playerRepository: PlayerRepository,
) {

    suspend fun execute(): String? {
        val userId = playerRepository.getUserIdentifier()
        val game = playerRepository.searchSessionByUser(userId) ?: return null
        val ready = game.firstPlayer != null && game.secondPlayer != null
        return if (ready) {
            game.sessionId
        } else {
            null
        }
    }
}