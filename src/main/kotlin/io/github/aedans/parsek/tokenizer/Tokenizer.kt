@file:Suppress("unused")

package io.github.aedans.parsek.tokenizer

import org.intellij.lang.annotations.Language
import java.io.File
import java.io.InputStream
import java.util.*
import java.util.regex.Pattern

/**
 * Info representing a token.
 *
 * @param type    The type of the token for later parsing.
 * @param pattern The regex pattern of the token.
 */
data class TokenInfo<out T>(
        val type: T,
        val pattern: Pattern
)

/**
 * Builder for creating a list of TokenInfo.
 */
fun <T> tokens(builder: MutableList<TokenInfo<T>>.() -> Unit) = run {
    val list = mutableListOf<TokenInfo<T>>()
    list.builder()
    list as List<TokenInfo<T>>
}

/**
 * Adds a token's info to a mutable list.
 *
 * @see TokenInfo
 */
fun <T> MutableList<TokenInfo<T>>.token(
        type: T,
        @Language("regexp") pattern: String
) = add(TokenInfo(type, "($pattern)".toPattern()))

/**
 * Returns a lazy sequence of tokens from a scanner.
 * If unable to match the entire scanner, throws a NoPatternMatchedException.
 *
 * @param tokens The list of tokens to parse.
 */
fun <T> Scanner.tokenize(tokens: List<TokenInfo<T>>) = generateSequence {
    useDelimiter("")
    if (hasNext()) {
        tokenizeOne(tokens)
    } else {
        null
    }
}

fun <T> String.tokenize(tokens: List<TokenInfo<T>>) = Scanner(this).tokenize(tokens)
fun <T> File.tokenize(tokens: List<TokenInfo<T>>) = Scanner(this).tokenize(tokens)
fun <T> InputStream.tokenize(tokens: List<TokenInfo<T>>) = Scanner(this).tokenize(tokens)
fun <T> Readable.tokenize(tokens: List<TokenInfo<T>>) = Scanner(this).tokenize(tokens)

/**
 * @see tokenize
 */
class NoPatternMatchedException : Exception("No pattern matched")

private tailrec fun <T> Scanner.tokenizeOne(tokens: List<TokenInfo<T>>): Token<T> =
        if (tokens.isEmpty()) {
            throw NoPatternMatchedException()
        } else {
            val (type, pattern) = tokens.first()
            if (!hasNext(pattern)) {
                tokenizeOne(tokens.drop(1))
            } else {
                skip(pattern)
                val token = match().group()
                Token(token, type)
            }
        }
