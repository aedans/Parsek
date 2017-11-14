package io.github.aedans.parsek

import io.kotlintest.matchers.should
import io.kotlintest.specs.StringSpec

class ConditionParserTest : StringSpec() {
    private val isEvenParser = conditionParser(
            { it: Int -> it % 2 == 0 },
            { "$it is even" },
            { "$it is not even" }
    )

    init {
        "isEvenParser should succeed on all even numbers" {
            for (i in (0..100).filter { it % 2 == 0 })
                isEvenParser(sequenceOf(i)) should succeed
        }

        "isEvenParser should fail on all odd numbers" {
            for (i in (0..100).filter { it % 2 == 1 })
                isEvenParser(sequenceOf(i)) should fail
        }
    }
}
