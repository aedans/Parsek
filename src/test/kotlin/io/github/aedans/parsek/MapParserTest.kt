package io.github.aedans.parsek

import io.kotlintest.matchers.*
import io.kotlintest.specs.StringSpec

class MapParserTest : StringSpec() {
    init {
        "Mapping successParser should succeed" {
            mapParser(successParser<Int>()) { it }(sequenceOf(0)) should succeed
        }

        "Mapping failureParser should fail" {
            mapParser(failureParser<Int>()) { it }(sequenceOf(0)) should fail
        }

        "Mapping an integer parser's result by * 2 should double the output" {
            mapParser(conditionParser(
                    { true },
                    { it: Int -> it }
            )) { it.copy(result = it.result * 2) }(sequenceOf(50)).toSuccessOrExcept().result shouldBe 100
        }
    }
}