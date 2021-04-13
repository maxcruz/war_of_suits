package com.maxcruz.core.extensions

import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test
import java.io.IOException

class ErrorHandlingKtTest {

    @Test
    fun `GIVEN an expected error WHEN an error happens THEN should be handled`() {
        runBlocking {
            val flow = flow<Int> { throw IOException() }
                .catchTyped(IOException::class) { emit(1) }
            val result = flow.single()
            result shouldBeEqualTo 1
        }
    }

    @Test(expected = IllegalStateException::class)
    fun `GIVEN an unexpected error WHEN an error happens THEN should crash`() {
        runBlocking {
            flow<Int> { throw IllegalStateException() }
                .catchTyped(IOException::class) { emit(1) }
                .collect { }
        }
    }

}