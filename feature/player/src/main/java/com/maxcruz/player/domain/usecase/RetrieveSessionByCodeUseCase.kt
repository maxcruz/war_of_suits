package com.maxcruz.player.domain.usecase

import com.maxcruz.player.domain.model.Session
import com.maxcruz.player.domain.repository.PlayerRepository
import javax.inject.Inject

class RetrieveSessionByCodeUseCase @Inject constructor(
    private val repository: PlayerRepository,
) {

    suspend fun execute(code: String): Session? = repository.searchSessionByCode(code)
}