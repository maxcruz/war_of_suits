package com.maxcruz.data.datasource

import com.maxcruz.data.dto.SessionDTO

interface SessionDataSource {

    suspend fun searchSessionByUser(userId: String): SessionDTO?

    suspend fun searchSessionByCode(code: String): SessionDTO?

    suspend fun updateSecondPlayer(sessionId: String, pLayer: String)

    suspend fun createSession(sessionId: String, code: String, pLayer: String)
}