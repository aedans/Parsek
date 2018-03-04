package io.github.aedans.parsek.syntax

import io.github.aedans.kons.Cons
import io.github.aedans.parsek.*
import io.github.aedans.parsek.combinators.*

data class SkipParser<I, out O, S>(val parser: Parser<I, O, S>) : Parser<I, O, S> {
    override fun invoke(input: Cons<I>, state: S) = parser(input, state)
}

fun <I, O, S> skip(parser: Parser<I, O, S>) = SkipParser(parser)

infix fun <I, O1, O2, S> Parser<I, O1, S>.then(parser: Parser<I, O2, S>) = thenParser(this, parser)
infix fun <I, O1, S> Parser<I, O1, S>.then(parser: SkipParser<I, *, S>) = thenParser(this, parser) map { a }
infix fun <I, O, S> SkipParser<I, *, S>.then(parser: Parser<I, O, S>) = thenParser(this, parser) map { b }
infix fun <I, S> SkipParser<I, *, S>.then(parser: SkipParser<I, *, S>) = skip(thenParser(this, parser))

infix fun <I, O, S> Parser<I, O, S>.or(parser: Parser<I, O, S>) = orParser(this, parser)

infix fun <I, O1, O2, S> Parser<I, O1, S>.map(map: O1.(S) -> O2) = mapParser(this, map)
infix fun <I, O1, O2, S> Parser<I, O1, S>.flatMap(map: O1.(S) -> ParseResult<I, O2, S>) = flatMapParser(this, map)
infix fun <I, O, S> Parser<I, O, S>.mapState(map: O.(S) -> S) = mapStateParser(this, map)

fun <I, O, S> optional(parser: Parser<I, O, S>) = optionalParser(parser)

fun <T, S> literal(t: T) = condition<T, S>(t.toString()) { it == t }
fun <T, S> condition(message: String = "", fn: (T) -> Boolean) = conditionalParser<T, S>(message, fn)
inline fun <T, reified A, S> instance() = instanceParser<T, A, S>()

fun <I, O, S> list(parser: Parser<I, O, S>) = listParser(parser)
fun <I, O, S> nel(parser: Parser<I, O, S>) = nelParser(parser)

fun <I, O, S> parser(fn: () -> Parser<I, O, S>): Parser<I, O, S> = run {
    val parser by lazy(fn);
    { input, state -> parser(input, state) }
}
