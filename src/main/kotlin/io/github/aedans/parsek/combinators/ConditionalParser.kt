package io.github.aedans.parsek.combinators

import arrow.core.Eval
import io.github.aedans.kons.*
import io.github.aedans.parsek.ParseResult.*
import io.github.aedans.parsek.Parser

fun <T, S> conditionalParser(
        message: String,
        fn: (T) -> Boolean
): Parser<T, T, S> = { input, state ->
    when (input) {
        Nil -> EOF
        is Cell -> {
            val it = input.car
            if (fn(it)) Success(input.cdr, it, state) else Failure(Eval.later { "Expected $message, found ${input.car}" })
        }
    }
}
