package io.github.aedans.parsek

import io.kotlintest.matchers.should
import io.kotlintest.specs.StringSpec

class OrParserTest : StringSpec() {
    private val intSequence = (0..100).asSequence()

    init {
        "or(failureParser, failureParser) should fail" {
            orParser(failureParser<Int>(), failureParser())(intSequence) should fail
        }

        "or(failureParser, successParser) should succeed" {
            orParser(failureParser<Int>(), successParser())(intSequence) should succeed
        }

        "or(successParser, failureParser) should succeed" {
            orParser(successParser<Int>(), failureParser())(intSequence) should succeed
        }

        "or(successParser, successParser) should succeed" {
            orParser(successParser<Int>(), successParser())(intSequence) should succeed
        }
    }
}
