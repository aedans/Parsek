package io.github.aedans.parsek

import io.kotlintest.matchers.should
import io.kotlintest.matchers.shouldEqual
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
            val result = sequenceParser(mapParser(conditionParser(
                    { true },
                    { it: Int -> it }
            )) { it.copy(result = it.result * 2) })((0..100).asSequence()).result
            result.zip((0..100).asSequence().map { it * 2 })
                    .forEach { (a, b) -> a shouldEqual b }
        }
    }
}