package io.github.aedans.parsek

import arrow.core.Eval
import io.github.aedans.kons.*
import io.github.aedans.parsek.ParseResult.*

typealias Parser<I, O, S> = (input: Cons<I>, state: S) -> ParseResult<I, O, S>

fun <I, O, S> Parser<I, O, S>.parseAll(input: Cons<I>, state: S): Cons<O> = run {
    val result = this(input, state)
    when (result) {
        EOF -> Nil
        is Success -> result.result cons Eval.later { parseAll(result.rest, result.state) }
        is Failure -> throw Exception(result.string.value())
    }
}
