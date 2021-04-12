package com.maxcruz.player.domain.usecase

import com.maxcruz.player.domain.repository.PlayerRepository
import javax.inject.Inject

class PlayerJoinSessionUseCase @Inject constructor(
    private val repository: PlayerRepository,
) {

    suspend fun execute(code: String): JoinResult? {
        val session = repository.searchSessionByCode(code)
        if (session != null) {
            val userId = repository.getUserIdentifier()
            if (repository.joinPlayer(session.sessionId, userId)) {
                return JoinResult(session.sessionId, userId)
            }
        }
        return null
    }

    data class JoinResult(val sessionId: String, val userId: String)
}