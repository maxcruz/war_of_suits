package com.maxcruz.data.datasource

import com.maxcruz.data.dto.SessionDTO
import kotlinx.coroutines.flow.Flow

interface SessionDataSource {

    suspend fun searchSessionByUser(userId: String): SessionDTO?

    suspend fun searchSessionByCode(code: String): SessionDTO?

    fun waitForSecondPlayer(code: String): Flow<SessionDTO>

    suspend fun secondPlayer(sessionId: String, pLayer: String)

    suspend fun createSession(sessionId: String, code: String, pLayer: String)
}