package io.github.aedans.parsek.dsl

import io.github.aedans.parsek.Parser
import io.github.aedans.parsek.thenParser

/**
 * Creates a parser that does skips its output.
 *
 * @param parser The parser to skip.
 */
fun <A, B> skip(parser: Parser<A, B>) = SkipParser(parser)

/**
 * Marker class for skipping
 */
class SkipParser<out A, out B>(private val parser: Parser<A, B>) : Parser<@UnsafeVariance A, B> by parser

/**
 * Overload for then that ignores the SkipParser's argument.
 *
 * @see thenParser
 */
infix fun <A, B2> SkipParser<A, *>.then(parser: Parser<A, B2>) =
        thenParser(this, parser) map { it.second }

/**
 * Overload for then that ignores the SkipParser's argument.
 *
 * @see thenParser
 */
infix fun <A, B1> Parser<A, B1>.then(parser: SkipParser<A, *>) =
        thenParser(this, parser) map { it.first }
