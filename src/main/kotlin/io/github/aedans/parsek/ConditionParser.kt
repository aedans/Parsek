package io.github.aedans.parsek

/**
 * Creates a parser that succeeds on a condition of a single token.
 *
 * @param test    The condition to test.
 * @param success The result to return on success.
 */
fun <A, B> conditionParser(
        test: (A) -> Boolean,
        success: (A) -> B
): Parser<A, B> = { input ->
    if (input.none()) {
        ParseResult.Failure(input) { "Input is empty" }
    } else {
        val it = input.first()
        if (test(it))
            ParseResult.Success(input.drop(1), success(it))
        else
            ParseResult.Failure(input) { "Condition $test failed for $it" }
    }
}
