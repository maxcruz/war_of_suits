package com.maxcruz.player.domain.repository

import com.maxcruz.data.datasource.PreferencesDataSource
import com.maxcruz.data.datasource.SessionDataSource
import com.maxcruz.data.dto.SessionDTO
import com.maxcruz.player.domain.model.Session
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
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
     * Return an available game with a player code
     */
    suspend fun searchSessionByCode(code: String): Session? =
        sessionDataSource.searchSessionByCode(code)?.map()

    /**
     * Second player joins to the game
     */
    suspend fun joinPlayer(sessionId: String, pLayer: String): Boolean {
        sessionDataSource.secondPlayer(sessionId, pLayer)
        return true
    }

    /**
     * Emits with the session ID when the second player enters
     */
    fun waitSecondPlayer(code: String): Flow<String> =
        sessionDataSource.waitForSecondPlayer(code)
            .map { it.sessionId }

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