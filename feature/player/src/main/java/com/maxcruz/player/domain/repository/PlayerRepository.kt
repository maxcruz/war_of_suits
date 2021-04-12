package com.maxcruz.player.domain.repository

import com.maxcruz.data.datasource.PreferencesDataSource
import com.maxcruz.data.datasource.SessionDataSource
import com.maxcruz.data.dto.SessionDTO
import com.maxcruz.player.domain.model.Session
import java.util.*
import javax.inject.Inject

/**
 * Player related data access
 */
class PlayerRepository @Inject constructor(
    private val preferencesDataSource: PreferencesDataSource,
    private val sessionDataSource: SessionDataSource,
) {

    /**
     * Retrieve or generate the user identifier.
     */
    fun getUserIdentifier(): String {
        val userId = preferencesDataSource.getUserIdentifier()
        return if (userId == null) {
            val generateId = UUID.randomUUID().toString()
            preferencesDataSource.saveUserIdentifier(generateId)
            generateId.takeLast(5).toUpperCase(Locale.getDefault())
        } else {
            userId.takeLast(5).toUpperCase(Locale.getDefault())
        }
    }

    /**
     * Return if the user is participating in an active game, null otherwise
     */
    suspend fun searchSessionByUser(userId: String): Session? =
        sessionDataSource.searchSessionByUser(userId)?.map()

    /**
     * Return an available game with a second player code
     */
    suspend fun searchSessionByCode(code: String): Session? =
        sessionDataSource.searchSessionByCode(code)?.map()

    suspend fun joinSecondPlayer(sessionId: String, secondPLayer: String): Boolean {
        sessionDataSource.updateSecondPlayer(sessionId, secondPLayer)
        return true
    }

    /**
     * Create a new game session and return the code or retrieve an existing one
     */
    suspend fun createOrRetrieveSession(firstPlayer: String): String {
        val sessionId = UUID.randomUUID().toString()
        val code = (10000..99999).random().toString()
        sessionDataSource.createSession(sessionId, code, firstPlayer)
        return code
    }

    private fun SessionDTO.map() : Session =
        Session(
            sessionId = this.sessionId,
            code = this.code,
            firstPlayer = this.game.firstPlayer,
            secondPlayer = this.game.secondPlayer
        )
}