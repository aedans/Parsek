@file:Suppress("unused")

package io.github.aedans.parsek.grammar

import io.github.aedans.parsek.Parser
import io.github.aedans.parsek.parseAll
import io.github.aedans.parsek.tokenizer.Token
import io.github.aedans.parsek.tokenizer.TokenInfo
import io.github.aedans.parsek.tokenizer.tokenize
import java.io.File
import java.io.InputStream
import java.util.*

/**
 * Interface for token-based grammars.
 *
 * @param T The type of the token.
 * @param E The type of the parser output.
 */
interface Grammar<T, out E> {
    val root: Parser<Token<T>, E>
    val tokens: List<TokenInfo<T>>
}

fun <T, E> Grammar<T, E>.parseAll(scanner: Scanner) = root.parseAll(scanner.tokenize(tokens))
fun <T, E> Grammar<T, E>.parseAll(string: String) = parseAll(Scanner(string))
fun <T, E> Grammar<T, E>.parseAll(file: File) = parseAll(Scanner(file))
fun <T, E> Grammar<T, E>.parseAll(inputStream: InputStream) = parseAll(Scanner(inputStream))
fun <T, E> Grammar<T, E>.parseAll(readable: Readable) = parseAll(Scanner(readable))
