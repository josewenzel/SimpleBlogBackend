package dev.wenzel.domain.exceptions

class PostDoesNotExistException(slug: String) : RuntimeException("A post with '${slug}' slug does not exist")
