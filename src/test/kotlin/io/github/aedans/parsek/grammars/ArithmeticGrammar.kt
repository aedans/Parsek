@file:Suppress("MemberVisibilityCanPrivate")

package io.github.aedans.parsek.grammars

import io.github.aedans.parsek.*
import io.github.aedans.parsek.dsl.*
import io.github.aedans.parsek.grammar.Grammar
import io.github.aedans.parsek.tokenizer.*

private typealias ExpressionParser = Parser<Token<ArithmeticGrammar.TokenType>, ArithmeticGrammar.Expr>

object ArithmeticGrammar : Grammar<ArithmeticGrammar.TokenType, ArithmeticGrammar.Expr> {
    sealed class Expr {
        data class Int(val int: kotlin.Int) : Expr()
        data class Plus(val expr1: Expr, val expr2: Expr) : Expr()
        data class Times(val expr1: Expr, val expr2: Expr) : Expr()
    }

    enum class TokenType(val regexp: String) : TokenParser<TokenType> {
        WS("\\s"), INT("[0-9]+"), PLUS("\\+"), TIMES("\\*"), OPEN_PAREN("\\("), CLOSE_PAREN("\\)");

        private val parser = parser { tokenParser(this, ignore = listOf(WS)) }
        override fun invoke(p1: Sequence<Token<TokenType>>) = parser(p1)
    }

    override val tokens = TokenType.values().map { TokenInfo(it, it.regexp.toPattern()) }

    override val root: ExpressionParser = parser(this::expression)

    val expression: ExpressionParser = parser(this::additiveExpression)

    val additiveExpression: ExpressionParser =
            parser(this::multiplicativeExpression) then
                    optional(skip(TokenType.PLUS) then
                            parser(this::additiveExpression)) map { (a, b) ->
                if (b != null) Expr.Plus(a, b) else a
            }

    val multiplicativeExpression: ExpressionParser =
            parser(this::atomicExpression) then
                    optional(skip(TokenType.TIMES) then
                            parser(this::multiplicativeExpression)) map { (a, b) ->
                if (b != null) Expr.Times(a, b) else a
            }

    val atomicExpression: ExpressionParser =
            parser(this::intExpression) or parser(this::parenExpression)

    val intExpression: ExpressionParser =
            TokenType.INT map { Expr.Int(it.text.toInt()) }

    val parenExpression: ExpressionParser =
            skip(TokenType.OPEN_PAREN) then parser(this::expression) then skip(TokenType.CLOSE_PAREN)
}
