package com.maxcruz.core.extensions

/**
 * This is going to cache the result of a pure function given the same input
 */
fun <T, R> ((T) -> R).memoize(): (T) -> R = Memoize(this)

internal class Memoize<in I, out O>(val function: (I) -> O) : (I) -> O {

    private val values = mutableMapOf<I, O>()

    override fun invoke(input: I): O = values.getOrPut(input, { function(input) })
}