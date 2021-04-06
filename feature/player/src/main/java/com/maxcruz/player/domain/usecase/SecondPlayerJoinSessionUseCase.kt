package com.maxcruz.player.domain.usecase

import com.maxcruz.player.domain.model.Player
import com.maxcruz.player.domain.repository.PlayerRepository
import javax.inject.Inject

class SecondPlayerJoinSessionUseCase @Inject constructor(
    private val repository: PlayerRepository,
) {

    suspend fun execute(code: String): String? {
        val session = repository.searchSessionByCode(code)
        if (session != null) {
            val userId = repository.getUserIdentifier()
            if (session.secondPlayer == null) {
                val player = Player.SecondPlayer(userId)
                val sessionUpdate = session.copy(secondPlayer = player)
                if (repository.updateSession(sessionUpdate)) {
                    return session.sessionId
                }
            }
        }
        return null
    }
}