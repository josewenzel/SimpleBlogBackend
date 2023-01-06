package dev.wenzel.domain.validators

class PostValidator {
    fun isValidSlug(slug: String): Boolean {
        return !slug.contains(" ") && isKebabCase(slug)
    }

    private fun isKebabCase(text: String) = text.matches(Regex("^([a-z][a-z0-9]*)(-[a-z0-9]+)*\$"))
}
