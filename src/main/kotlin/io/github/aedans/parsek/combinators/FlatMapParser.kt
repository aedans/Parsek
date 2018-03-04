package io.github.aedans.parsek.combinators

import io.github.aedans.parsek.*
import io.github.aedans.parsek.ParseResult.*

fun <I, O1, O2, S> flatMapParser(
        parser: Parser<I, O1, S>,
        map: O1.(S) -> ParseResult<I, O2, S>
): Parser<I, O2, S> = { input, state ->
    val parse = parser(input, state)
    when (parse) {
        EOF -> EOF
        is Failure -> parse
        is Success -> {
            val result = parse.result.map(parse.state)
            when (result) {
                EOF -> EOF
                is Failure -> result
                is Success -> Success(result.rest, result.result, result.state)
            }
        }
    }
}
