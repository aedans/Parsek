package io.github.aedans.parsek

import kotlin.coroutines.experimental.buildSequence

/**
 * Parses an entire sequence and returns a lazy sequence of results.
 * Throws a ParseException when a parse fails.
 *
 * @param input The sequence to parse.
 */
fun <A, B> Parser<A, B>.parseAll(input: Sequence<A>): Sequence<B> = buildSequence {
    @Suppress("NAME_SHADOWING")
    var input = input

    while (input.any()) {
        val it = this@parseAll(input)
        when (it) {
            is ParseResult.Success -> {
                input = it.rest
                yield(it.result)
            }
            is ParseResult.Failure -> throw ParseException(it)
        }
    }
}
