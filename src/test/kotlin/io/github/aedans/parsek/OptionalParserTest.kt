package io.github.aedans.parsek

import io.kotlintest.matchers.should
import io.kotlintest.matchers.shouldEqual
import io.kotlintest.specs.StringSpec

class OptionalParserTest : StringSpec() {
    init {
        "optional(successParser) should succeed" {
            optional(successParser<Int>())(sequenceOf(0)) should succeed
        }

        "optional(failureParser) should succeed" {
            optional(failureParser<Int>())(sequenceOf(0)) should succeed
        }

        "optional(failureParser) should return null" {
            optional(failureParser<Int>())(sequenceOf(0)).toSuccessOrExcept().result shouldEqual null
        }
    }
}