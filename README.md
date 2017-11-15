Parsek
======

A fast and extensible parser combinator library for [Kotlin](http://kotlinlang.org) based on
[Parsec](https://github.com/haskell/parsec).

Using with gradle
-----------------

    repositories {
        jcenter()
    }

    dependencies {
        compile 'io.github.aedans:parsek:1.0.0'
    }

Example
-------

```kotlin
typealias BooleanParser = Parser<Token<BooleanGrammar.TokenType>, Boolean>

object BooleanGrammar : Grammar<BooleanGrammar.TokenType, Boolean> {
    enum class TokenType : TokenParser<TokenType> {
        WHITESPACE, TRUE, FALSE, AND, OR, NOT, OPEN_PAREN, CLOSE_PAREN;

        private val parser = parser { tokenParser(this, ignore = listOf(WHITESPACE)) }
        override fun invoke(p1: Sequence<Token<TokenType>>) = parser(p1)
    }

    override val tokens = tokens<TokenType> {
        token(TokenType.WHITESPACE, "\\s")
        token(TokenType.TRUE, "true")
        token(TokenType.FALSE, "false")
        token(TokenType.AND, "&")
        token(TokenType.OR, "\\|")
        token(TokenType.NOT, "!")
        token(TokenType.OPEN_PAREN, "\\(")
        token(TokenType.CLOSE_PAREN, "\\)")
    }

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

Why Parsek?
-----------

- Very fast 
- Simple and extendable DSL
- Input is parsed lazily, output is generated lazily
- Tokens maintain source position
- Arbitrary input: Tokens and Grammar are just Parser specializations
- Support for semantic newlines/whitespace
