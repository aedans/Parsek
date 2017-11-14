package io.github.aedans.parsek

import io.kotlintest.matchers.should
import io.kotlintest.matchers.shouldEqual
import io.kotlintest.specs.StringSpec

class MapParserTest : StringSpec() {
    init {
        "Mapping successParser should succeed" {
            mapParser(successParser<Int>()) {  }(sequenceOf(0)) should succeed
        }

        "Mapping failureParser should fail" {
            mapParser(failureParser<Int>()) {  }(sequenceOf(0)) should fail
        }

        "Mapping an integer parser by * 2 should double the output" {
            val result = mapParser(conditionParser(
                    { true },
                    { it: Int -> it }
            )) { it * 2 }.parseAll((0..100).asSequence())
            result.zip((0..100).asSequence().map { it * 2 })
                    .forEach { (a, b) -> a shouldEqual b }
        }
    }
}