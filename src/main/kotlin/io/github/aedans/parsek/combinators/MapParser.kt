package io.github.aedans.parsek.combinators

import io.github.aedans.parsek.ParseResult.*
import io.github.aedans.parsek.Parser

fun <I, O1, O2, S> mapParser(
        parser: Parser<I, O1, S>,
        map: O1.(S) -> O2
): Parser<I, O2, S> = { input, state ->
    val parse = parser(input, state)
    when (parse) {
        EOF -> EOF
        is Failure -> parse
        is Success -> Success(parse.rest, parse.result.map(parse.state), parse.state)
    }
}
