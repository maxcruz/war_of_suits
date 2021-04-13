package com.maxcruz.core.extensions

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class MemoizeTest {

    private val mock: DummyProvider = mockk()
    private val function = { parameter: String -> mock.provideInt(parameter) }.memoize()

    @Before
    fun setUp() {
        every { mock.provideInt(any()) } returns 5
    }

    @Test
    fun `GIVEN a memoized function WHEN is called twice with same parameter THEN should execute once`() {
        function("foo")
        function("foo")
        verify(exactly = 1) { mock.provideInt(any()) }
    }

    @Test
    fun `GIVEN a memoized function WHEN is called twice with different parameter THEN should execute twice`() {
        function("foo")
        function("faa")
        verify(exactly = 2) { mock.provideInt(any()) }
    }

    interface DummyProvider {
        fun provideInt(parameter: String): Int
    }
}