package io.github.aedans.parsek

/**
 * Creates a parser that matches a literal.
 *
 * @param literal The literal object to match.
 */
fun <A> literalParser(literal: A) = conditionParser({ it: A -> it == literal }, { it })
