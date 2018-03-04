package io.github.aedans.parsek.combinators

import io.github.aedans.kons.*
import io.github.aedans.parsek.Parser
import io.github.aedans.parsek.syntax.*

fun <I, O, S> nelParser(parser: Parser<I, O, S>): Parser<I, Cons<O>, S> = parser then list(parser) map { a cons b }
