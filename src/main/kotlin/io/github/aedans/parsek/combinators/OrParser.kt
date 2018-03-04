package io.github.aedans.parsek.combinators

import arrow.core.Eval
import io.github.aedans.parsek.ParseResult.*
import io.github.aedans.parsek.Parser

fun <I, O, S> orParser(
        first: Parser<I, O, S>,
        second: Parser<I, O, S>
): Parser<I, O, S> = { input, state ->
    val result1 = first(input, state)
    when (result1) {
        EOF -> EOF
        is Success -> result1
        is Failure -> {
            val result2 = second(input, state)
            when (result2) {
                EOF -> EOF
                is Success -> result2
                is Failure -> Failure(Eval.later { "No valid alternatives for ${input.car ?: EOF}" })
            }
        }
    }
}
