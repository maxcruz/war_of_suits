package com.maxcruz.player.domain.repository

import com.maxcruz.data.datasource.PreferencesDataSource
import com.maxcruz.data.datasource.SessionDataSource
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Before
import org.junit.Test

class PlayerRepositoryTest {

    private val mockDataSource: SessionDataSource = mockk()
    private val mockPreferenceDataSource: PreferencesDataSource = mockk()
    private lateinit var repository: PlayerRepository

    @Before
    fun setUp() {
        repository = PlayerRepository(mockPreferenceDataSource, mockDataSource)
    }

    @Test
    fun `GIVEN a saved user identifier WHEN the repository is asked THEN should return it without create a new one`() {
        every { mockPreferenceDataSource.getUserIdentifier() } returns "FOO"
        every { mockPreferenceDataSource.saveUserIdentifier(any()) } throws IllegalStateException()

        repository.getUserIdentifier()

        verify(exactly = 1) { mockPreferenceDataSource.getUserIdentifier() }
        verify(exactly = 0) { mockPreferenceDataSource.saveUserIdentifier(any()) }
    }

    @Test
    fun `GIVEN a create session request THEN should generate the session ID`() {
        val userId = "foo"
        val sessionSlot = slot<String>()
        val codeSlot = slot<String>()
        coEvery {
            mockDataSource.createSession(capture(sessionSlot), capture(codeSlot), any())
        } answers {}

        runBlocking {
            repository.createOrRetrieveSession(userId)
        }

        sessionSlot.captured.isNotBlank() shouldBeEqualTo true
        codeSlot.captured.isNotBlank() shouldBeEqualTo true
    }

}