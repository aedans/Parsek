package io.github.aedans.parsek

/**
 * Creates a parser that succeeds on a condition of a single token.
 *
 * @param test    The condition to test.
 * @param success The result to return on success.
 * @param failure The result to return on failure.
 */
fun <A, B, C> conditionParser(
        test: (A) -> Boolean,
        success: (A) -> B,
        failure: (A) -> C
): Parser<A, B, C> = { input ->
    val it = input.first()
    if (test(it))
        ParseResult.Success(input.drop(1), success(it))
    else
        ParseResult.Failure(failure(it))
}
