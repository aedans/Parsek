package io.github.aedans.parsek

/**
 * Creates a parser that maps a parser's success result.
 *
 * @param parser The parser to map.
 * @param map    The mapping function.
 */
fun <A, B, C> mapParser(
        parser: Parser<A, B>,
        map: (B) -> C
) = { input: Sequence<A> ->
    val parseResult = parser(input)
    when (parseResult) {
        is ParseResult.Success -> ParseResult.Success(parseResult.rest, map(parseResult.result))
        is ParseResult.Failure -> parseResult
    }
}
