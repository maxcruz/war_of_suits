package com.maxcruz.player.domain.repository

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
    suspend fun searchGameSession(userId: String): String? {
        return null
    }

    /**
     * Returns true if both players are in the session and is active, false otherwise
     */
    suspend fun isSessionReady(sessionId: String) : Boolean {
        return false
    }

    /**
     * Remove a game session from the user
     */
    suspend fun clearPlayerSession(userId: String) {

    }
}