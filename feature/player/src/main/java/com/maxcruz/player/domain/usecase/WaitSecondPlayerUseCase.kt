package com.maxcruz.player.domain.usecase

import com.maxcruz.player.domain.repository.PlayerRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case that emit a session ID when a player joins with the given code
 */
class WaitSecondPlayerUseCase @Inject constructor(
    private val repository: PlayerRepository,
) {

    fun execute(code: String): Flow<String> = repository.waitSecondPlayer(code)
}