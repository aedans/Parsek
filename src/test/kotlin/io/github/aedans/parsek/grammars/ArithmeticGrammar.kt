@file:Suppress("MemberVisibilityCanPrivate")

package io.github.aedans.parsek.grammars

import io.github.aedans.parsek.*
import io.github.aedans.parsek.dsl.*
import io.github.aedans.parsek.grammar.Grammar
import io.github.aedans.parsek.tokenizer.*

private typealias ExprParser = Parser<Token<ArithmeticGrammar.TokenType>, ArithmeticGrammar.Expr>

object ArithmeticGrammar : Grammar<ArithmeticGrammar.TokenType, ArithmeticGrammar.Expr> {
    enum class TokenType : TokenParser<TokenType> {
        WS, INT, PLUS, TIMES, OPEN_PAREN, CLOSE_PAREN;

        private val parser: TokenParser<TokenType> = parser { tokenParser(this, ignore = listOf(WS)) }
        override fun invoke(p1: Sequence<Token<TokenType>>) = parser(p1)
    }

    sealed class Expr {
        data class Int(val int: kotlin.Int) : Expr()
        data class Plus(val expr1: Expr, val expr2: Expr) : Expr()
        data class Times(val expr1: Expr, val expr2: Expr) : Expr()
    }

    override val tokens = tokens<TokenType> {
        token(TokenType.WS, "\\s")
        token(TokenType.INT, "[0-9]+")
        token(TokenType.PLUS, "\\+")
        token(TokenType.TIMES, "\\*")
        token(TokenType.OPEN_PAREN, "\\(")
        token(TokenType.CLOSE_PAREN, "\\)")
    }

    override val root: ExprParser = parser(this::expression)

    val expression: ExprParser = parser(this::additiveExpression)

    val additiveExpression: ExprParser =
            parser(this::multiplicativeExpression) then
                    optional(skip(TokenType.PLUS) then
                            parser(this::additiveExpression)) map { (a, b) ->
                if (b != null) Expr.Plus(a, b) else a
            }

    val multiplicativeExpression: ExprParser =
            parser(this::atomicExpression) then
                    optional(skip(TokenType.TIMES) then
                            parser(this::multiplicativeExpression)) map { (a, b) ->
                if (b != null) Expr.Times(a, b) else a
            }

    val atomicExpression: ExprParser =
            parser(this::intExpression) or parser(this::parenExpression)

    val intExpression: ExprParser =
            TokenType.INT map { Expr.Int(it.text.toInt()) }

    val parenExpression: ExprParser =
            skip(TokenType.OPEN_PAREN) then parser(this::expression) then skip(TokenType.CLOSE_PAREN)
}
