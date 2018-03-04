package io.github.aedans.parsek.combinators

import arrow.core.*
import io.github.aedans.parsek.ParseResult.*
import io.github.aedans.parsek.Parser

fun <I, O1, O2, S> thenParser(
        first: Parser<I, O1, S>,
        second: Parser<I, O2, S>
): Parser<I, Tuple2<O1, O2>, S> = { input, state ->
    val result1 = first(input, state)
    when (result1) {
        EOF -> EOF
        is Failure -> result1
        is Success -> {
            val result2 = second(result1.rest, result1.state)
            when (result2) {
                EOF -> EOF
                is Failure -> result2
                is Success -> Success(result2.rest, result1.result toT result2.result, result2.state)
            }
        }
    }
}
