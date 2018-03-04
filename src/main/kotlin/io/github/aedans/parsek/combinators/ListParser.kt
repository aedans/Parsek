package io.github.aedans.parsek.combinators

import arrow.core.Eval
import io.github.aedans.kons.*
import io.github.aedans.parsek.ParseResult.*
import io.github.aedans.parsek.Parser

fun <I, O, S> listParser(parser: Parser<I, O, S>): Parser<I, Cons<O>, S> = { input, state ->
    fun parseList(input: Cons<I>, state: S): Eval<Success<I, Cons<O>, S>> = run {
        val result = parser(input, state)
        when (result) {
            EOF, is Failure -> Eval.now(Success(input, Nil, state))
            is Success -> Eval.defer { parseList(result.rest, result.state) }
                    .map { Success(it.rest, result.result cons it.result, it.state) }
        }
    }

    parseList(input, state).value()
}
