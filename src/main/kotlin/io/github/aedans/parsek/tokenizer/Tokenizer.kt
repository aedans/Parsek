@file:Suppress("unused")

package io.github.aedans.parsek.tokenizer

import io.github.aedans.parsek.memoizedSequence
import org.intellij.lang.annotations.Language
import java.io.File
import java.io.InputStream
import java.util.*
import java.util.regex.Pattern
import kotlin.NoSuchElementException
import kotlin.coroutines.experimental.buildSequence

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
 * If unable to match the entire scanner, throws an UnexpectedCharacterException.
 *
 * @param tokens The list of tokens to parse.
 */
fun <A> Scanner.tokenize(tokens: List<TokenInfo<A>>) = buildSequence {
    useDelimiter("")
    var row = 1
    var col = 1

    while (hasNext()) {
        val token = tokenizeOne(row, col, tokens)

        // row/col code adapted from com.github.h0tk3y.betterParse
        col += token.text.length

        val addRows = token.text.count { it == '\n' }
        row += addRows
        if (addRows > 0)
            col = token.text.length - token.text.lastIndexOf('\n')

        yield(token)
    }
}.memoizedSequence

fun <A> String.tokenize(tokens: List<TokenInfo<A>>) = Scanner(this).tokenize(tokens)
fun <A> File.tokenize(tokens: List<TokenInfo<A>>) = Scanner(this).tokenize(tokens)
fun <A> InputStream.tokenize(tokens: List<TokenInfo<A>>) = Scanner(this).tokenize(tokens)
fun <A> Readable.tokenize(tokens: List<TokenInfo<A>>) = Scanner(this).tokenize(tokens)

/**
 * @see tokenize
 */
class UnexpectedCharacterException(string: String) : Exception("Unexpected $string")

private fun <A> Scanner.tokenizeOne(row: Int, col: Int, tokens: List<TokenInfo<A>>): Token<A> =
        if (tokens.isEmpty()) {
            throw UnexpectedCharacterException(next(".*"))
        } else {
            val (type, pattern) = tokens.first()
            // Scanner code adapted from com.github.h0tk3y.betterParse
            try {
                skip(pattern)
                val token = match().group()
                Token(token, type, row, col)
            } catch (_: NoSuchElementException) {
                tokenizeOne(row, col, tokens.drop(1))
            }
        }
