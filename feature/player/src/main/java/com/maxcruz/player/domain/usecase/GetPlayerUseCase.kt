package com.maxcruz.player.domain.usecase

import com.maxcruz.player.domain.model.Player
import com.maxcruz.player.domain.repository.PlayerRepository
import javax.inject.Inject

/**
 * Retrieve an user identified as a player
 */
class GetPlayerUseCase @Inject constructor(
    private val playerRepository: PlayerRepository
) {

    suspend fun execute(option: NewGameOption) : Player {
        val userId = playerRepository.getUserIdentifier()
        return when (option) {
            NewGameOption.Join -> {
                Player.SecondPlayer(userId)
            }
            NewGameOption.Start -> {
                Player.FirstPlayer(userId)
            }
        }
    }

    sealed class NewGameOption {
        object Start : NewGameOption()
        object Join : NewGameOption()
    }
}