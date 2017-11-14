package io.github.aedans.parsek.tokenizer

import io.github.aedans.parsek.Parser
import io.github.aedans.parsek.conditionParser

/**
 * Creates a parser that parses tokens of type T.
 *
 * @param type The type of the token.
 */
fun <A> tokenParser(type: A) = conditionParser({ it: Token<A> -> it.type == type }, { it })

typealias TokenParser<A> = Parser<Token<A>, Token<A>>
