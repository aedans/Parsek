package io.github.aedans.parsek.tokenizer

/**
 * Class representing a single token.
 *
 * @param text The text of the token.
 * @param type The type of the token.
 * @param row  The row of the token in its source (1-indexed).
 * @param col  The column of the token in its source (1-indexed).
 */
data class Token<out A>(
        val text: String,
        val type: A,
        val row: Int,
        val col: Int
) {
    override fun toString() = "Token(" +
            "text=\"${text
                    .replace("\r", "\\r")
                    .replace("\n", "\\n")
                    .replace("\t", "\\t")
                    .replace("\"", "\\\"")}\", " +
            "type=$type, " +
            "row=$row, " +
            "col=$col)"
}
