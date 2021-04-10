package com.maxcruz.player.domain.usecase

import com.maxcruz.core.domain.model.Player
import com.maxcruz.core.domain.model.Role
import com.maxcruz.player.domain.repository.PlayerRepository
import javax.inject.Inject

/**
 * Search an stored game session for the given user ID
 */
class RecoverSessionUseCase @Inject constructor(
    private val playerRepository: PlayerRepository,
) {

    suspend fun execute(): SessionFound? {
        val userId = playerRepository.getUserIdentifier()
        val game = playerRepository.searchSessionByUser(userId) ?: return null
        val ready = game.firstPlayer != null && game.secondPlayer != null
        return if (ready) {
            val role = if (userId == game.firstPlayer) Role.FIRST else Role.SECOND
            SessionFound(game.sessionId, Player(userId, role))
        } else {
            null
        }
    }

    data class SessionFound(val sessionId: String, val player: Player)
}