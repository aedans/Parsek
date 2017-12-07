package io.github.aedans.parsek

import io.kotlintest.matchers.*
import io.kotlintest.specs.StringSpec

class ParseAllTest : StringSpec() {
    init {
        "successParser.parseAll should succeed" {
            successParser<Int>().parseAll((0..100).asSequence()).forEach {  }
        }

        "failureParser.parseAll should fail" {
            shouldThrow<ParseException> {
                failureParser<Int>().parseAll((0..100).asSequence()).forEach {  }
            }
        }

        "parseAll should be in order" {
            identityParser<Int>().parseAll((0..100).asSequence()).toList() shouldEqual (0..100).toList()
        }

        "parseAll should be lazy" {
            val sequence = generateSequence { 0 }.memoizedSequence
            val result = identityParser<Int>().parseAll(sequence)
            result.take(10)
        }
    }
}
