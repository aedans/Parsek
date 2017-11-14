package io.github.aedans.parsek

val <A> Sequence<A>.memoizedSequence get() = run  {
    val iterator = iterator()
    val computed = mutableListOf<A>()
    object : Sequence<A> {
        override fun iterator() = object : Iterator<A> {
            var index = 0
            override fun hasNext() = index < computed.size || iterator.hasNext()

            override fun next()= run {
                fill()
                computed[index++]
            }

            tailrec fun fill() {
                if (computed.size <= index) {
                    computed.add(iterator.next())
                    fill()
                }
            }
        }
    }
}

/**
 * Converts a ParseResult to a ParseResult.Success or null.
 */
fun <A, B> ParseResult<A, B>.toSuccess() = this as? ParseResult.Success

/**
 * Converts a ParseResult to a ParseResult.Success or throws an exception.
 */
fun <A, B> ParseResult<A, B>.toSuccessOrExcept() = toSuccess()
        ?: throw Exception("Unexpected $this")
