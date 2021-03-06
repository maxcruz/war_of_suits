package com.maxcruz.player.domain.usecase

import com.maxcruz.player.domain.repository.PlayerRepository
import javax.inject.Inject

/**
 * Create the game and retrieve the code
 */
class FirstPlayerStartSessionUseCase @Inject constructor(
    private val playerRepository: PlayerRepository,
) {

    suspend fun execute(): JoinSecondPlayer {
        val userId = playerRepository.getUserIdentifier()
        val code = playerRepository.createOrRetrieveSession(userId)
        return JoinSecondPlayer(code)
    }

    data class JoinSecondPlayer(val code: String)
}