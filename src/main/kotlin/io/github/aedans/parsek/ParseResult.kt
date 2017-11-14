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
    data class Failure(private val errF: () -> String) : ParseResult<Nothing, Nothing>() {
        val err get() = errF()
    }
}
