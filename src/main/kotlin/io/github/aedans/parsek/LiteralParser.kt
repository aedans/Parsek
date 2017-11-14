package io.github.aedans.parsek

/**
 * Creates a parser that matches a literal.
 *
 * @param literal The literal object to match.
 * @param failure The error to return on failure.
 */
fun <A, C> literalParser(
        literal: A,
        failure: (A) -> C
) = conditionParser({ it == literal }, { it }, failure)
