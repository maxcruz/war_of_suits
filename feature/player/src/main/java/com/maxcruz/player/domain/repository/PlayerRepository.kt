package com.maxcruz.player.domain.repository

import com.maxcruz.player.domain.model.Game
import com.maxcruz.player.domain.model.Player
import javax.inject.Inject

/**
 * Player related data access
 */
class PlayerRepository @Inject constructor(

) {

    /**
     * Retrieve or generate the user identifier.
     */
    suspend fun getUserIdentifier(): String {
        return "USER001"
    }

    /**
     * Search a game session by user ID or null if not exist
     */
    suspend fun searchGameSession(userId: String): Game? {
        return null
    }

    /**
     * Create a new game session and return the code or retrieve an existing one
     */
    suspend fun createOrRetrieveGame(firstPlayer: Player.FirstPlayer): Game {
        return Game(sessionId = "xyz123456", code = "ABC123", firstPlayer = firstPlayer, secondPlayer = null)
    }
}