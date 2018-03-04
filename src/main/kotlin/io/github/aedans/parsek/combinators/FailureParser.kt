package io.github.aedans.parsek.combinators

import io.github.aedans.parsek.*

fun <A, B, C> failureParser(): Parser<A, B, C> = { _, _ -> ParseResult.Failure() }
