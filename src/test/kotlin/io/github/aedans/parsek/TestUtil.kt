package io.github.aedans.parsek

import io.kotlintest.matchers.beInstanceOf

fun <A> successParser() = conditionParser({ _: A  -> true }, {  }, {  })
fun <A> failureParser() = conditionParser({ _: A  -> false }, {  }, {  })
fun <A> identityParser() = conditionParser({ _: A -> true }, { it }, {  })

val succeed = beInstanceOf(ParseResult.Success::class)
val fail = beInstanceOf(ParseResult.Failure::class)

val unitFailure = ParseResult.Failure(Unit)
