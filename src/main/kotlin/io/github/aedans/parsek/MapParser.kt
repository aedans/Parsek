package io.github.aedans.parsek

/**
 * Creates a parser that maps a parser's success result.
 *
 * @param parser The parser to map.
 * @param map    The mapping function.
 */
fun <A, B, C, D> mapParser(
        parser: Parser<A, B>,
        map: (ParseResult.Success<A, B>) -> ParseResult<C, D>
) = { input: Sequence<A> ->
    val parseResult = parser(input)
    when (parseResult) {
        is ParseResult.Success -> map(parseResult)
        is ParseResult.Failure -> parseResult
    }
}
