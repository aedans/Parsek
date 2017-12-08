Parsek
======

[![Download](https://api.bintray.com/packages/aedans/maven/parsek/images/download.svg)](https://bintray.com/aedans/maven/parsek/_latestVersion)

A fast and extensible parser combinator library for [Kotlin](http://kotlinlang.org) based on
[Parsec](https://github.com/haskell/parsec).

Gradle
-----------------

```gradle
repositories {
    maven { url 'https://dl.bintray.com/aedans/maven/' }
}

dependencies {
    compile 'io.github.aedans:parsek:1.2.4'
}
```

Features
--------

- Standard parser combinators
- Simple and extensible DSL
- Completely lazy
- Tokens maintain source position
- Arbitrary input: Tokens and Grammar are just Parser specializations
- Support for semantic newlines/whitespace

Example Boolean Grammar
-----------------------

```kotlin
typealias BooleanParser = Parser<Token<BooleanGrammar.TokenType>, Boolean>

object BooleanGrammar : Grammar<BooleanGrammar.TokenType, Boolean> {
    enum class TokenType(val regexp: String) : TokenParser<TokenType> {
        WS("\\s"), TRUE("true"), FALSE("false"), AND("&"), OR("\\|"), NOT("!"), OPEN_PAREN("\\("), CLOSE_PAREN("\\)");

        private val parser = parser { tokenParser(this, ignore = listOf(WS)) }
        override fun invoke(p1: Sequence<Token<TokenType>>) = parser(p1)
    }

    override val tokens = TokenType.values().map { TokenInfo(it, it.regexp.toPattern()) }

    val trueParser = TokenType.TRUE map { true }
    val falseParser = TokenType.FALSE map { false }
    val parenParser = skip(TokenType.OPEN_PAREN) then parser(this::expressionParser) then skip(TokenType.CLOSE_PAREN)
    val notParser = skip(TokenType.NOT) then parser(this::atomicParser) map { !it }
    val atomicParser: BooleanParser = trueParser or falseParser or parenParser or notParser
    val andParser: BooleanParser = atomicParser then skip(TokenType.AND) then parser(this::andParser) map { (a, b) -> a && b } or atomicParser
    val orParser: BooleanParser = andParser then skip(TokenType.OR) then parser(this::orParser) map { (a, b) -> a || b } or andParser
    val expressionParser: BooleanParser = orParser

    override val root = expressionParser
}

val result = BooleanGrammar("...")
```

More example grammars are available [here](https://github.com/aedans/parsek/tree/master/src/test/kotlin/io/github/aedans/parsek/grammars)
