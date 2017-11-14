package io.github.aedans.parsek

import io.kotlintest.matchers.should
import io.kotlintest.specs.StringSpec

class ThenParserTest : StringSpec() {
    private val intSequence = (0..100).asSequence()

    init {
        "then(failureParser, failureParser) should fail" {
            thenParser(failureParser<Int>(), failureParser())(intSequence) should fail
        }

        "then(failureParser, successParser) should fail" {
            thenParser(failureParser<Int>(), successParser())(intSequence) should fail
        }

        "then(successParser, failureParser) should fail" {
            thenParser(successParser<Int>(), failureParser())(intSequence) should fail
        }

        "then(successParser, successParser) should succeed" {
            thenParser(successParser<Int>(), successParser())(intSequence) should succeed
        }
    }
}