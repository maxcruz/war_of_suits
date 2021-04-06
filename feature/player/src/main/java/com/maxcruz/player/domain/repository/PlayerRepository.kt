package com.maxcruz.player.domain.repository

import com.maxcruz.player.domain.model.Player
import com.maxcruz.player.domain.model.Session
import javax.inject.Inject

/**
 * Player related data access
 */
class PlayerRepository @Inject constructor(
    // Here goes data module dependency
) {

    /**
     * Retrieve or generate the user identifier.
     */
    suspend fun getUserIdentifier(): String {
        return "USER001"
    }

    /**
     * Return if the user is participating in an active game, null otherwise
     */
    suspend fun searchSessionByUser(userId: String): Session? {
        return null
    }

    /**
     * Return an available game with a second player code
     */
    suspend fun searchSessionByCode(code: String): Session? {
        return null
    }

    suspend fun updateSession(session: Session) : Boolean {
        return false
    }

    /**
     * Create a new game session and return the code or retrieve an existing one
     */
    suspend fun createOrRetrieveSession(firstPlayer: Player.FirstPlayer): Session {
        return Session(sessionId = "xyz123456", code = "ABC123", firstPlayer = firstPlayer, secondPlayer = null)
    }
}