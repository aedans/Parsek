@file:Suppress("MemberVisibilityCanPrivate")

package io.github.aedans.parsek.grammars

import io.github.aedans.parsek.Parser
import io.github.aedans.parsek.dsl.*
import io.github.aedans.parsek.grammar.Grammar
import io.github.aedans.parsek.tokenizer.*

typealias BooleanParser = Parser<Token<BooleanGrammar.TokenType>, Boolean>

object BooleanGrammar : Grammar<BooleanGrammar.TokenType, Boolean> {
    enum class TokenType(val regexp: String) : TokenParser<TokenType> {
        WS("\\s"), TRUE("true"), FALSE("false"), AND("&"), OR("\\|"), NOT("!"), OPEN_PAREN("\\("), CLOSE_PAREN("\\)");

        private val parser = parser { tokenParser(this, ignore = listOf(WS)) }
        override fun invoke(p1: Sequence<Token<TokenType>>) = parser(p1)
    }

    override val tokens = TokenType.values().map { TokenInfo(it, it.regexp.toPattern()) }

    val trueParser = TokenType.TRUE map { true }
    val falseParser = TokenType.FALSE map { false }
    val parenParser = skip(TokenType.OPEN_PAREN) then parser(this::expressionParser) then skip(TokenType.CLOSE_PAREN)
    val notParser = skip(TokenType.NOT) then parser(this::atomicParser) map { !it }
    val atomicParser: BooleanParser = trueParser or falseParser or parenParser or notParser
    val andParser: BooleanParser = atomicParser then skip(TokenType.AND) then parser(this::andParser) map { (a, b) -> a && b } or atomicParser
    val orParser: BooleanParser = andParser then skip(TokenType.OR) then parser(this::orParser) map { (a, b) -> a || b } or andParser
    val expressionParser: BooleanParser = orParser

    override val root = expressionParser
}
