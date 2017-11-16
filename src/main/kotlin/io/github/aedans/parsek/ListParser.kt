package io.github.aedans.parsek

/**
 * Parses a list of elements.
 *
 * @param parser The parser to parse a single element.
 */
fun <A, B> listParser(parser: Parser<A, B>) = { input: Sequence<A> ->
    tailrec fun parseAcc(input: Sequence<A>, acc: List<B>): ParseResult<A, List<B>> {
        val result = parser(input)
        return when (result) {
            is ParseResult.Success -> parseAcc(result.rest, acc + result.result)
            is ParseResult.Failure -> ParseResult.Success(result.rest, acc)
        }
    }

    parseAcc(input, emptyList())
}
