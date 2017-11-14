@file:Suppress("unused")

package io.github.aedans.parsek.tokenizer

import org.intellij.lang.annotations.Language
import java.io.File
import java.io.InputStream
import java.util.*
import java.util.regex.Pattern

data class TokenInfo(
        val pattern: Pattern,
        val type: String
)

fun tokens(builder: MutableList<TokenInfo>.() -> Unit) = run {
    val list = mutableListOf<TokenInfo>()
    list.builder()
    list as List<TokenInfo>
}

fun MutableList<TokenInfo>.token(
        type: String,
        @Language("regexp") pattern: String
) = add(TokenInfo("($pattern)".toPattern(), type))

fun String.tokenize(tokens: List<TokenInfo>) = Scanner(this).tokenize(tokens)
fun File.tokenize(tokens: List<TokenInfo>) = Scanner(this).tokenize(tokens)
fun InputStream.tokenize(tokens: List<TokenInfo>) = Scanner(this).tokenize(tokens)
fun Readable.tokenize(tokens: List<TokenInfo>) = Scanner(this).tokenize(tokens)
fun Scanner.tokenize(tokens: List<TokenInfo>) = generateSequence {
    useDelimiter("")
    if (hasNext()) {
        tokenizeOne(tokens)
    } else {
        null
    }
}

private tailrec fun Scanner.tokenizeOne(tokens: List<TokenInfo>): Token =
        if (tokens.isEmpty()) {
            throw Exception("No patterns matched")
        } else {
            val (pattern, type) = tokens.first()
            if (!hasNext(pattern)) {
                tokenizeOne(tokens.drop(1))
            } else {
                skip(pattern)
                val token = match().group()
                Token(token, type)
            }
        }
