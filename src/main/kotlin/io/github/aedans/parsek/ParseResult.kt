package io.github.aedans.parsek

import arrow.core.Eval
import io.github.aedans.kons.Cons

@Suppress("unused")
sealed class ParseResult<out I, out O, out S> {
    data class Success<out I, out O, out S>(val rest: Cons<I>, val result: O, val state: S) : ParseResult<I, O, S>()
    data class Failure(val string: Eval<String> = Eval.later { "" }) : ParseResult<Nothing, Nothing, Nothing>()
    object EOF : ParseResult<Nothing, Nothing, Nothing>()
}