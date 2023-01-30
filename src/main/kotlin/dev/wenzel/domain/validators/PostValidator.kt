package dev.wenzel.domain.validators

import dev.wenzel.domain.exceptions.InvalidSlugException


class PostValidator {
    fun validateSlug(slug: String): Boolean {
        if (isKebabCase(slug)) return true else throw InvalidSlugException(slug)
    }

    private fun isKebabCase(text: String) = text.matches(Regex("^([a-z][a-z0-9]*)(-[a-z0-9]+)*\$"))
}
