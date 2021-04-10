package com.maxcruz.player.domain.repository

import com.maxcruz.player.domain.model.Session
import javax.inject.Inject

/**
 * Player related data access
 * TODO: Connect this with a data source
 */
class PlayerRepository @Inject constructor(
    // Here goes data module dependency
) {

    var session = Session(sessionId = "xyz123456", code = "ABC123", firstPlayer = null, secondPlayer = null)
    val user = "USER001"

    /**
     * Retrieve or generate the user identifier.
     */
    suspend fun getUserIdentifier(): String {
        return user
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
        return session
    }

    suspend fun updateSession(session: Session) : Boolean {
        this.session = session
        return true
    }

    /**
     * Create a new game session and return the code or retrieve an existing one
     */
    suspend fun createOrRetrieveSession(firstPlayer: String): Session {
        return session.copy(firstPlayer = firstPlayer)
    }
}