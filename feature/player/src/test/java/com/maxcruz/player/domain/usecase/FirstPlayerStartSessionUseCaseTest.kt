package com.maxcruz.player.domain.usecase

import com.maxcruz.player.domain.repository.PlayerRepository
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Before
import org.junit.Test

class FirstPlayerStartSessionUseCaseTest {

    private var mockPlayerRepository: PlayerRepository = mockk()
    private lateinit var useCase: FirstPlayerStartSessionUseCase

    @Before
    fun setUp() {
        useCase = FirstPlayerStartSessionUseCase(mockPlayerRepository)
    }

    @Test
    fun `WHEN the use case is executed THEN should return the session code`() {
        val code = "FOO"
        every { mockPlayerRepository.getUserIdentifier() } returns "123"
        coEvery { mockPlayerRepository.createOrRetrieveSession(any()) } returns code

        val result = runBlocking {
            useCase.execute()
        }

        result shouldBeEqualTo FirstPlayerStartSessionUseCase.JoinSecondPlayer(code)
    }

}