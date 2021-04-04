package com.maxcruz.player.domain.usecase

import com.maxcruz.player.domain.model.Player
import com.maxcruz.player.domain.repository.PlayerRepository
import javax.inject.Inject

class FirstPlayerStartGameUseCase @Inject constructor(
    private val playerRepository: PlayerRepository
) {

    suspend fun execute(player: Player.FirstPlayer) {

    }
}