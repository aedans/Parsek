@file:Suppress("unused")

package io.github.aedans.parsek.tokenizer

import io.github.aedans.parsek.memoizedSequence
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
data class TokenInfo<out A>(
        val type: A,
        val pattern: Pattern
)

/**
 * Builder for creating a list of TokenInfo.
 */
fun <A> tokens(builder: MutableList<TokenInfo<A>>.() -> Unit) = run {
    val list = mutableListOf<TokenInfo<A>>()
    list.builder()
    list as List<TokenInfo<A>>
}

/**
 * Adds a token's info to a mutable list.
 *
 * @see TokenInfo
 */
fun <A> MutableList<TokenInfo<A>>.token(
        type: A,
        @Language("regexp") pattern: String
) = add(TokenInfo(type, "($pattern)".toPattern()))

/**
 * Returns a lazy sequence of tokens from a scanner.
 * If unable to match the entire scanner, throws a NoPatternMatchedException.
 *
 * @param tokens The list of tokens to parse.
 */
fun <A> Scanner.tokenize(tokens: List<TokenInfo<A>>) = generateSequence {
    useDelimiter("")
    if (hasNext()) {
        tokenizeOne(tokens)
    } else {
        null
    }
}.memoizedSequence

fun <A> String.tokenize(tokens: List<TokenInfo<A>>) = Scanner(this).tokenize(tokens)
fun <A> File.tokenize(tokens: List<TokenInfo<A>>) = Scanner(this).tokenize(tokens)
fun <A> InputStream.tokenize(tokens: List<TokenInfo<A>>) = Scanner(this).tokenize(tokens)
fun <A> Readable.tokenize(tokens: List<TokenInfo<A>>) = Scanner(this).tokenize(tokens)

/**
 * @see tokenize
 */
class NoPatternMatchedException : Exception("No pattern matched")

private tailrec fun <A> Scanner.tokenizeOne(tokens: List<TokenInfo<A>>): Token<A> =
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
