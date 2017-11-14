package io.github.aedans.parsek

/**
 * The result of parsing a sequence.
 *
 * @see Parser
 */
@Suppress("unused")
sealed class ParseResult<out A, out B> {
    /**
     * Class representing a successful parse result.
     *
     * @param rest   The remaining sequence to be parser.
     * @param result The result of the parse.
     */
    data class Success<out A, out B>(
            val rest: Sequence<A>,
            val result: B
    ) : ParseResult<A, B>()

    /**
     * Class representing a parse failure.
     */
    class Failure<out A>(
            val rest: Sequence<A>,
            private val errF: () -> String
    ) : ParseResult<A, Nothing>() {
        val err get() = errF()
        override fun toString() = "Failure: $err"
    }
}
