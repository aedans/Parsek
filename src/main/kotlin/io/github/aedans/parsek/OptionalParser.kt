package io.github.aedans.parsek

/**
 * Creates a parser that returns null instead of failing.
 *
 * @param parser The parser to test.
 */
fun <A, B> optional(parser: Parser<A, B>) = { input: Sequence<A> ->
    val parseResult = parser(input)
    when (parseResult) {
        is ParseResult.Success -> parseResult
        is ParseResult.Failure -> ParseResult.Success(input, null)
    }
}