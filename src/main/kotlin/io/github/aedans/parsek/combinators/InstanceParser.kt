package io.github.aedans.parsek.combinators

import arrow.core.Eval
import io.github.aedans.kons.*
import io.github.aedans.parsek.*

@Suppress("UNCHECKED_CAST")
inline fun <T, reified A, S> instanceParser(): Parser<T, A, S> = { input, state ->
    when (input) {
        Nil -> ParseResult.EOF
        is Cell -> {
            val it = input.car
            when (it) {
                is A -> ParseResult.Success(input.cdr, it as A, state)
                else -> ParseResult.Failure(Eval.later { "Expected ${A::class.java}, found ${input.car}" })
            }
        }
    }
}
