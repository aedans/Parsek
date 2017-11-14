package io.github.aedans.parsek

/**
 * Exception thrown on an non-propagateable parse error.
 */
class ParseException(failure: ParseResult.Failure) : Exception("Parse exception: ${failure.err}")
