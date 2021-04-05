package com.maxcruz.player.domain.usecase

import com.maxcruz.player.domain.model.Player
import com.maxcruz.player.domain.repository.PlayerRepository
import javax.inject.Inject

/**
 * Create the game and retrieve the code
 */
class FirstPlayerStartGameUseCase @Inject constructor(
    private val playerRepository: PlayerRepository,
) {

    suspend fun execute(): StartGame {
        val userId = playerRepository.getUserIdentifier()
        val game = playerRepository.createOrRetrieveGame(Player.FirstPlayer(userId))
        return if (game.secondPlayer == null) {
            StartGame.JoinSecondPlayer(game.code)
        } else {
            StartGame.GameStarted(game.sessionId)
        }
    }

    sealed class StartGame {
        data class JoinSecondPlayer(val code: String) : StartGame()
        data class GameStarted(val sessionId: String) : StartGame()
    }
}