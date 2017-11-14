package io.github.aedans.parsek

/**
 * Exception thrown on an non-propagateable parse error.
 */
class ParseException(val parseResult: ParseResult<*, *>) : Exception("Parse exception: $parseResult")
