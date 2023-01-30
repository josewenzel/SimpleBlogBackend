package dev.wenzel.domain.exceptions

class InvalidSlugException(slug: String) : RuntimeException("'${slug}' is not a valid slug")
