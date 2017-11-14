package io.github.aedans.parsek

import io.kotlintest.matchers.should
import io.kotlintest.specs.StringSpec

class LiteralParserTest : StringSpec() {
    private val parseA = literalParser('a')

    init {
        "parseA should succeed on a" {
            parseA("aaaaa".asSequence()) should succeed
        }

        "parserA should fail on not a" {
            for (c in (0..128).map { it.toChar() } - 'a')
                parseA(sequenceOf(c)) should fail
        }
    }
}
