package io.github.aedans.parsek.tokenizer

import io.github.aedans.parsek.conditionParser

/**
 * Creates a parser that parses tokens of type T.
 *
 * @param type The type of the token.
 */
fun <T> tokenParser(type: T) = conditionParser({ it: Token<T> -> it.type == type }, { it })
