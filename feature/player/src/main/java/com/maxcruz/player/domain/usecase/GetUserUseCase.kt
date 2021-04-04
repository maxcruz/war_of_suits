package com.maxcruz.player.domain.usecase

import com.maxcruz.player.domain.repository.PlayerRepository
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val playerRepository: PlayerRepository
) {

    suspend fun execute() : String = playerRepository.getUserIdentifier()
}