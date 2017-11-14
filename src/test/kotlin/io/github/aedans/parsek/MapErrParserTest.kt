package io.github.aedans.parsek

import io.kotlintest.matchers.should
import io.kotlintest.matchers.shouldEqual
import io.kotlintest.specs.StringSpec

class MapErrParserTest : StringSpec() {
    init {
        "Mapping successParser error should succeed" {
            mapErrParser(successParser<Int>()) { it }(sequenceOf(0)) should succeed
        }

        "Mapping failureParser should fail" {
            mapErrParser(failureParser<Int>()) { it }(sequenceOf(0)) should fail
        }
    }
}