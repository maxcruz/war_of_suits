package com.maxcruz.player.domain.usecase

import com.maxcruz.player.domain.repository.PlayerRepository
import javax.inject.Inject

class SecondPlayerJoinSessionUseCase @Inject constructor(
    private val repository: PlayerRepository,
) {

    suspend fun execute(code: String): JoinResult? {
        val session = repository.searchSessionByCode(code)
        if (session != null) {
            val userId = repository.getUserIdentifier()
            if (session.secondPlayer == null) {
                val sessionUpdate = session.copy(secondPlayer = userId)
                if (repository.updateSession(sessionUpdate)) {
                    return JoinResult(session.sessionId, userId)
                }
            }
        }
        return null
    }

    data class JoinResult(val sessionId: String, val userId: String)
}