package io.github.aedans.parsek.dsl

import io.github.aedans.parsek.*

/**
 * @see mapParser
 */
infix fun <A, B, C> Parser<A, B>.map(map: (B) -> C) =
        mapParser(this) { ParseResult.Success(it.rest, map(it.result)) }

/**
 * @see mapErr
 */
infix fun <A, B> Parser<A, B>.mapErr(map: (String) -> String) =
        mapErrParser(this) { ParseResult.Failure(it.rest) { map(it.err) } }

/**
 * @see orParser
 */
infix fun <A, B> Parser<A, B>.or(parser: Parser<A, B>) = orParser(this, parser)

/**
 * @see thenParser
 */
infix fun <A, B1, B2> Parser<A, B1>.then(parser: Parser<A, B2>) = thenParser(this, parser)

/**
 * Creates a parser that is evaluated lazily.
 * This allows recursive and bi-recursive parsers, as well as parsers that depend on uninitialized objects.
 *
 * @param parser The function to compute the parser.
 */
fun <A, B> parser(parser: () -> Parser<A, B>) = run {
    val p by lazy(parser);
    { input: Sequence<A> -> p(input) }
}
