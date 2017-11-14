package io.github.aedans.parsek

/**
 * The core parser type.
 *
 * A parser takes in an input sequence and returns either the remaining sequence and a result or an error.
 *
 * @param A The type of the elements in the sequence.
 * @param B The type of the result.
 */
typealias Parser<A, B> = (Sequence<A>) -> ParseResult<A, B>
