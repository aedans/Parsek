package io.github.aedans.parsek

/**
 * Parses a sequence of elements.
 *
 * @param parser The parser to parse a single element.
 */
fun <A, B : Any> sequenceParser(parser: Parser<A, B>) = { input: Sequence<A> ->
    var rest = input

    val seq = generateSequence {
        val result = parser(rest)
        when (result) {
            is ParseResult.Success -> {
                rest = result.rest
                result.result
            }
            is ParseResult.Failure -> null
        }
    }

    ParseResult.Success(rest, seq)
}
