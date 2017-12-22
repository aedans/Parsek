package io.github.aedans.parsek

import io.github.aedans.parsek.grammar.*
import io.github.aedans.parsek.grammars.ArithmeticGrammar
import io.kotlintest.matchers.should
import io.kotlintest.matchers.shouldEqual
import io.kotlintest.specs.StringSpec

class ArithmeticTest : StringSpec() {
    init {
        "Parsing an empty string should fail" {
            ArithmeticGrammar("") should fail
        }

        "Parser should parse integers" {
            ArithmeticGrammar("019").toSuccessOrExcept().result shouldEqual ArithmeticGrammar.Expr.Int(19)
        }

        "Parser should ignore whitespace" {
            ArithmeticGrammar("   1") should succeed
        }

        "Parser should parse addition" {
            ArithmeticGrammar("5 + 10").toSuccessOrExcept().result shouldEqual
                    ArithmeticGrammar.Expr.Plus(
                            ArithmeticGrammar.Expr.Int(5),
                            ArithmeticGrammar.Expr.Int(10)
                    )
        }

        "Parser should parse multiplication" {
            ArithmeticGrammar("5 * 10").toSuccessOrExcept().result shouldEqual
                    ArithmeticGrammar.Expr.Times(
                            ArithmeticGrammar.Expr.Int(5),
                            ArithmeticGrammar.Expr.Int(10)
                    )
        }

        "Parser should parse parentheses" {
            ArithmeticGrammar("(0)").toSuccessOrExcept().result shouldEqual ArithmeticGrammar.Expr.Int(0)
        }

        "Addition should be right associative" {
            ArithmeticGrammar("1 + 2 + 3").toSuccessOrExcept().result shouldEqual
                    ArithmeticGrammar.Expr.Plus(
                            ArithmeticGrammar.Expr.Int(1),
                            ArithmeticGrammar.Expr.Plus(
                                    ArithmeticGrammar.Expr.Int(2),
                                    ArithmeticGrammar.Expr.Int(3)
                            )
                    )
        }

        "Multiplication should be right associative" {
            ArithmeticGrammar("1 * 2 * 3").toSuccessOrExcept().result shouldEqual
                    ArithmeticGrammar.Expr.Times(
                            ArithmeticGrammar.Expr.Int(1),
                            ArithmeticGrammar.Expr.Times(
                                    ArithmeticGrammar.Expr.Int(2),
                                    ArithmeticGrammar.Expr.Int(3)
                            )
                    )
        }

        "Multiplication should have a higher precedence than addition" {
            ArithmeticGrammar("1 * 2 + 3").toSuccessOrExcept().result shouldEqual
                    ArithmeticGrammar.Expr.Plus(
                            ArithmeticGrammar.Expr.Times(
                                    ArithmeticGrammar.Expr.Int(1),
                                    ArithmeticGrammar.Expr.Int(2)
                            ),
                            ArithmeticGrammar.Expr.Int(3)
                    )
        }

        "Parentheses should have a higher precedence than multiplication" {
            ArithmeticGrammar("(1 * 2) * 3").toSuccessOrExcept().result shouldEqual
                    ArithmeticGrammar.Expr.Times(
                            ArithmeticGrammar.Expr.Times(
                                    ArithmeticGrammar.Expr.Int(1),
                                    ArithmeticGrammar.Expr.Int(2)
                            ),
                            ArithmeticGrammar.Expr.Int(3)
                    )
        }

        "Parsing an incomplete expression should fail" {
            ArithmeticGrammar("(1 + )") should fail
        }
    }
}
