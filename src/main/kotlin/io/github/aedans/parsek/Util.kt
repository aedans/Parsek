package io.github.aedans.parsek

val <A> Sequence<A>.memoizedSequence get() = run  {
    val iterator = iterator()
    val computed = mutableListOf<A>()
    object : Sequence<A> {
        override fun iterator() = object : Iterator<A> {
            var index = 0
            override fun hasNext() = run {
                fill()
                iterator.hasNext()
            }

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
