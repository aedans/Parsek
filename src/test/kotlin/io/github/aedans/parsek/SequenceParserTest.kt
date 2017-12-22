package io.github.aedans.parsek

import io.kotlintest.matchers.should
import io.kotlintest.matchers.shouldEqual
import io.kotlintest.specs.StringSpec

class SequenceParserTest : StringSpec() {
    init {
        "sequenceParser(successParser) should succeed" {
            sequenceParser(successParser<Int>())((0..100).asSequence()) should succeed
        }

        "sequenceParser(failureParser) should succeed" {
            sequenceParser(failureParser<Int>())((0..100).asSequence()) should succeed
        }

        "sequenceParser(identityParser) should return the sequence" {
            sequenceParser(identityParser<Int>())((0..100).asSequence())
                    .toSuccessOrExcept().result.toList() shouldEqual (0..100).toList()
        }

        "sequenceParser should be stack safe" {
            sequenceParser(failureParser<Int>())((0..10000).asSequence()).toSuccessOrExcept().result.forEach {  }
        }
    }
}
