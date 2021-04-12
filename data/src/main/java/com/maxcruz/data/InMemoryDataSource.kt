package com.maxcruz.data

import android.content.Context
import com.maxcruz.data.datasource.GameDataSource
import com.maxcruz.data.datasource.PreferencesDataSource
import com.maxcruz.data.datasource.SessionDataSource
import com.maxcruz.data.dto.CardDTO
import com.maxcruz.data.dto.GameDTO
import com.maxcruz.data.dto.SessionDTO
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

private const val PREFERENCES_KEY = "war_of_suits_prefs"
private const val USER_KEY = "user_id"

/**
 * This class is intended to be a fake data source holding the data in-memory
 * Used for development purposes
 */
@Singleton
class InMemoryDataSource @Inject constructor(
    @ApplicationContext context: Context
): PreferencesDataSource, SessionDataSource, GameDataSource {

    private var storage = mutableMapOf<String, SessionDTO>()
    private val preferences = context.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE)

    // PreferencesDataSource
    override fun getUserIdentifier(): String? = preferences.getString(USER_KEY, null)

    override fun saveUserIdentifier(identifier: String) {
        with (preferences.edit()) {
            putString(USER_KEY, identifier)
            apply()
        }
    }

    // SessionDataSource
    override suspend fun searchSessionByUser(userId: String): SessionDTO? =
        storage.values.find { it.game.firstPlayer == userId && it.active }

    override suspend fun searchSessionByCode(code: String): SessionDTO? {
        return storage.values.find { it.code == code && it.active }
    }

    override suspend fun secondPlayer(sessionId: String, pLayer: String) {
        val session = storage[sessionId] ?: throw IllegalStateException("Session: $sessionId")
        val game = session.game
        storage[sessionId] = session.copy(game = game.copy(firstPlayer = pLayer))
    }

    override suspend fun createSession(sessionId: String, code: String, pLayer: String) {
        val session = SessionDTO(sessionId, code, GameDTO(pLayer), true)
        storage[sessionId] = session
    }

    override fun waitForSecondPlayer(code: String): Flow<SessionDTO> =
        flow {
            // Fake second player joining
            val session = searchSessionByCode(code) ?: throw IllegalStateException("Code: $code")
            session.game.secondPlayer = "FAKE"
            delay(3000)
            emit(session)
        }

    // GameDataSource
    override suspend fun setupSessionGame(
        sessionId: String,
        priority: List<String>,
        firstPlayerDeck: List<CardDTO>,
        secondPlayerDeck: List<CardDTO>,
    ) {
        val session = storage[sessionId] ?: throw IllegalStateException("Session: $sessionId")
        val game = session.game
        val updatedGame = game.copy(
            priority = priority,
            firstPlayerDeck = firstPlayerDeck.toMutableList(),
            secondPLayerDeck = secondPlayerDeck.toMutableList(),
        )
        storage[sessionId] = session.copy(game = updatedGame)
    }

    override suspend fun getSecondPlayerCard(sessionId: String): CardDTO {
        val session = storage[sessionId] ?: throw IllegalStateException("Session: $sessionId")
        return session.game.secondPLayerDeck.last()
    }

    override suspend fun removeLastCardsFromPlayers(sessionId: String) {
        val session = storage[sessionId] ?: throw IllegalStateException("Session: $sessionId")
        session.game.firstPlayerDeck.removeLast()
        session.game.secondPLayerDeck.removeLast()
    }

    override suspend fun getGamePriority(sessionId: String): List<String> {
        val session = storage[sessionId] ?: throw IllegalStateException("Session: $sessionId")
        return session.game.priority
    }

    override suspend fun updatePoints(sessionId: String, points: Pair<Int, Int>) {
        val session = storage[sessionId] ?: throw IllegalStateException("Session: $sessionId")
        session.game.pointsFirstPlayer += points.first
        session.game.pointsSecondPlayer += points.second
    }

    override suspend fun getGamePoints(sessionId: String): Pair<Int, Int> {
        val session = storage[sessionId] ?: throw IllegalStateException("Session: $sessionId")
        return session.game.pointsFirstPlayer to session.game.pointsSecondPlayer
    }

    override suspend fun setActiveFalse(sessionId: String) {
        val session = storage[sessionId] ?: throw IllegalStateException("Session: $sessionId")
        session.active = false
    }
}