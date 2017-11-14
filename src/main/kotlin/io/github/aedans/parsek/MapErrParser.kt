package io.github.aedans.parsek

/**
 * Creates a parser that maps a parser's failure result.
 *
 * @param parser The parser to map.
 * @param map    The mapping function.
 */
fun <A, B> mapErrParser(
        parser: Parser<A, B>,
        map: (ParseResult.Failure<A>) -> ParseResult<A, B>
) = { input: Sequence<A> ->
    val parseResult = parser(input)
    when (parseResult) {
        is ParseResult.Success -> parseResult
        is ParseResult.Failure -> map(parseResult)
    }
}
