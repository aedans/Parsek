package io.github.aedans.parsek.combinators

import io.github.aedans.parsek.ParseResult.*
import io.github.aedans.parsek.Parser

fun <I, O, S> mapStateParser(
        parser: Parser<I, O, S>,
        map: O.(S) -> S
): Parser<I, O, S> = { input, state ->
    val parse = parser(input, state)
    when (parse) {
        EOF -> EOF
        is Failure -> parse
        is Success -> Success(parse.rest, parse.result, parse.result.map(parse.state))
    }
}
