package io.github.aedans.parsek

import io.kotlintest.matchers.should
import io.kotlintest.specs.StringSpec

class AndParserTest : StringSpec() {
    private val intSequence = (0..100).asSequence()

    init {
        "and(failureParser, failureParser) should fail" {
            andParser(failureParser<Int>(), failureParser())(intSequence) should fail
        }

        "and(failureParser, successParser) should fail" {
            andParser(failureParser<Int>(), successParser())(intSequence) should fail
        }

        "and(successParser, failureParser) should fail" {
            andParser(successParser<Int>(), failureParser())(intSequence) should fail
        }

        "and(successParser, successParser) should succeed" {
            andParser(successParser<Int>(), successParser())(intSequence) should succeed
        }
    }
}