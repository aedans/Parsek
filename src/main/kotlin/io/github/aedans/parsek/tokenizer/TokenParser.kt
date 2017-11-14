package io.github.aedans.parsek.tokenizer

import io.github.aedans.parsek.Parser
import io.github.aedans.parsek.conditionParser
import io.github.aedans.parsek.dsl.or
import io.github.aedans.parsek.dsl.skip
import io.github.aedans.parsek.dsl.then

/**
 * Creates a parser that parses tokens of type T.
 *
 * @param type The type of the token.
 */
fun <A> tokenParser(type: A) = conditionParser({ it: Token<A> -> it.type == type }, { it })

/**
 * Creates a parser that parses tokens of type T.
 * When the parser encounters an unexpected token, if it is in the ignored list, it skips that token.
 *
 * @see tokenParser
 *
 * @param type   The type of the token.
 * @param ignore The list of token types to ignore.
 */
fun <A> tokenParser(type: A, ignore: List<A>): TokenParser<A> =
        ignore.fold(tokenParser(type)) { parser, it ->
            parser or (skip(tokenParser(it)) then io.github.aedans.parsek.dsl.parser { tokenParser(type, ignore) })
        }

typealias TokenParser<A> = Parser<Token<A>, Token<A>>
