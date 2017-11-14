package io.github.aedans.parsek

import io.github.aedans.cons.Cons
import io.github.aedans.cons.Nil
import io.github.aedans.cons.cons

/**
 * Parses an entire sequence and returns a lazy sequence of results.
 * Throws a ParseException when a parse fails.
 *
 * @param input The sequence to parse.
 */
fun <A, B, C> Parser<A, B, C>.parseAll(input: Sequence<A>): Sequence<B> = run {
    val parser = this
    fun parseAllCons(input: () -> Sequence<A>): Cons<B> =
        if (input().none()) {
            Nil
        } else {
            val result by lazy {
                val it = parser(input())
                when (it) {
                    is ParseResult.Success -> it.rest to it.result
                    is ParseResult.Failure -> throw ParseException(it)
                }
            };
            { result.second } cons { parseAllCons { result.first } }
        }

    parseAllCons { input }.asSequence()
}
