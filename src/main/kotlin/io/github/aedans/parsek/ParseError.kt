package io.github.aedans.parsek

/**
 * Interface for parser errors.
 */
interface ParseError {
    val message: String

    companion object {
        operator fun invoke(messageF: () -> String) = object : ParseError {
            override val message get() = messageF()
        }
    }
}
