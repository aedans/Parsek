package io.github.aedans.parsek.combinators

import arrow.core.*
import io.github.aedans.parsek.ParseResult.*
import io.github.aedans.parsek.Parser

fun <I, O, S> optionalParser(parser: Parser<I, O, S>): Parser<I, Option<O>, S> = { input, state ->
    val result = parser(input, state)
    when (result) {
        is Success -> Success(result.rest, Option.pure(result.result), result.state)
        EOF, is Failure -> Success(input, None, state)
    }
}
