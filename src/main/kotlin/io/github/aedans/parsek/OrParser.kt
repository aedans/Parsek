package io.github.aedans.parsek

/**
 * Creates a parser that succeeds if either parser succeeds.
 *
 * @param parser1 The first parser to test.
 * @param parser2 The second parser to test.
 */
fun <A, B, C1, C2> orParser(
        parser1: Parser<A, B, C1>,
        parser2: Parser<A, B, C2>
): Parser<A, B, Pair<C1, C2>> = { input ->
    val result1 = parser1(input)
    val result2 = parser2(input)
    when {
        result1 is ParseResult.Success -> result1
        result2 is ParseResult.Success -> result2
        else -> ParseResult.Failure((result1 as ParseResult.Failure<C1>).err to (result2 as ParseResult.Failure<C2>).err)
    }
}
