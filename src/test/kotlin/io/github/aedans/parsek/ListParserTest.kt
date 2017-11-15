package io.github.aedans.parsek

import io.kotlintest.matchers.should
import io.kotlintest.specs.StringSpec

class ListParserTest : StringSpec() {
    init {
        "listParser(successParser) should succeed" {
            listParser(successParser<Int>())((0..100).asSequence()) should succeed
        }

        "listParser(failureParser) should succeed" {
            listParser(failureParser<Int>())((0..100).asSequence()) should succeed
        }

        "listParser should be stack safe" {
            listParser(failureParser<Int>())((0..10000).asSequence()).toSuccessOrExcept().result.forEach {  }
        }
    }
}
