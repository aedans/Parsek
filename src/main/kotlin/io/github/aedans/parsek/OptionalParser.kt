package io.github.aedans.parsek

/**
 * Creates a parser that returns null instead of failing.
 *
 * @param parser The parser to test.
 */
fun <A, B> optional(parser: Parser<A, B>) = mapErrParser(parser) {
    ParseResult.Success(it.rest, null)
}