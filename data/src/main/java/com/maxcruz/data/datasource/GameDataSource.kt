package com.maxcruz.data.datasource

import com.maxcruz.data.dto.CardDTO

interface GameDataSource {

    suspend fun setupSessionGame(
        sessionId: String,
        priority: List<String>,
        firstPlayerDeck: List<CardDTO>,
        secondPlayerDeck: List<CardDTO>,
    )

    suspend fun getSecondPlayerCard(sessionId: String): CardDTO

    suspend fun removeLastCardsFromPlayers(sessionId: String)

    suspend fun getGamePriority(sessionId: String): List<String>

    suspend fun updatePoints(sessionId: String, points: Pair<Int, Int>)

    suspend fun getGamePoints(sessionId: String): Pair<Int, Int>

    suspend fun setActiveFalse(sessionId: String)
}