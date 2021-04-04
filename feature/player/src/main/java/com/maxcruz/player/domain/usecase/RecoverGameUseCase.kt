package com.maxcruz.player.domain.usecase

import com.maxcruz.player.domain.repository.PlayerRepository
import javax.inject.Inject

/**
 * Search an stored game session for the given user ID
 */
class RecoverGameUseCase @Inject constructor(
    private val playerRepository: PlayerRepository
) {

    suspend fun execute() : String? {
        val userId = playerRepository.getUserIdentifier()
        val gameSession = playerRepository.searchGameSession(userId)
        val ready = gameSession?.let { playerRepository.isSessionReady(it) } ?: false
        return if (ready) {
            gameSession
        } else {
            playerRepository.clearPlayerSession(userId)
            null
        }
    }
}