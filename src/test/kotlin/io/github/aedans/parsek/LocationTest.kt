package io.github.aedans.parsek

import io.github.aedans.parsek.tokenizer.token
import io.github.aedans.parsek.tokenizer.tokenize
import io.github.aedans.parsek.tokenizer.tokens
import io.kotlintest.matchers.shouldEqual
import io.kotlintest.specs.StringSpec
import java.util.*

class LocationTest : StringSpec() {
    private val tokens = tokens<Boolean> {
        token(true, "@")
        token(false, ".|\n")
    }

    init {
        "Token column should equal its column in source" {
            Scanner("@").tokenize(tokens).filter { it.type }.first().col shouldEqual 1
            Scanner("  @").tokenize(tokens).filter { it.type }.first().col shouldEqual 3
            Scanner("  \n@").tokenize(tokens).filter { it.type }.first().col shouldEqual 1
            Scanner("  \n  @").tokenize(tokens).filter { it.type }.first().col shouldEqual 3
        }

        "Token row should equal its row in source" {
            Scanner("@").tokenize(tokens).filter { it.type }.first().row shouldEqual 1
            Scanner("  @").tokenize(tokens).filter { it.type }.first().row shouldEqual 1
            Scanner("  \n@").tokenize(tokens).filter { it.type }.first().row shouldEqual 2
            Scanner("  \n  @").tokenize(tokens).filter { it.type }.first().row shouldEqual 2
        }
    }
}
