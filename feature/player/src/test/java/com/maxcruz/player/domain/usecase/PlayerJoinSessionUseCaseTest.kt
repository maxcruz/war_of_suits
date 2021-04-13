package com.maxcruz.player.domain.usecase

import com.maxcruz.player.domain.model.Session
import com.maxcruz.player.domain.repository.PlayerRepository
import com.maxcruz.player.domain.usecase.PlayerJoinSessionUseCase.JoinResult
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Before
import org.junit.Test

class PlayerJoinSessionUseCaseTest {

    private var mockRepository: PlayerRepository = mockk()
    private lateinit var useCase: PlayerJoinSessionUseCase

    @Before
    fun setUp() {
        useCase = PlayerJoinSessionUseCase(mockRepository)
    }

    @Test
    fun `WHEN the player joins the session THEN should return the session ID`() {
        val code = "FOO"
        val sessionId = "xyz"
        val userId = "123"
        val mockSession: Session = mockk()
        every { mockRepository.getUserIdentifier() } returns userId
        coEvery { mockRepository.searchSessionByCode(code) } returns mockSession
        every { mockSession.sessionId } returns sessionId
        coEvery { mockRepository.joinPlayer(sessionId, userId) } returns true

        val result = runBlocking {
            useCase.execute(code)
        }

        result shouldBeEqualTo JoinResult(sessionId, userId)
    }

}