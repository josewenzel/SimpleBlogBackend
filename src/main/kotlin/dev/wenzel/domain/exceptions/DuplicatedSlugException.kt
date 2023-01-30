package dev.wenzel.domain.exceptions

class DuplicatedSlugException(slug: String) : RuntimeException("'${slug}' slug already exists")
