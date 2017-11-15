package io.github.aedans.parsek

/**
 * Creates a parser that succeeds if either parser succeeds.
 *
 * @param parser1 The first parser to test.
 * @param parser2 The second parser to test.
 */
fun <A, B> orParser(
        parser1: Parser<A, B>,
        parser2: Parser<A, B>
): Parser<A, B> = { input ->
    val result1 = parser1(input)
    val result2 = parser2(input)
    when {
        result1 is ParseResult.Success -> result1
        result2 is ParseResult.Success -> result2
        else -> ParseResult.Failure(input) {
            "No valid alternatives for ${try {
                input.first()
            } catch (_: NoSuchElementException) {
                input
            }}"
        }
    }
}
