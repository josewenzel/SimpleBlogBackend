package dev.wenzel.domain.ports

import dev.wenzel.domain.model.Post

interface PostRepository {
    fun add(post: Post)
    fun getBySlug(slug: String): Post?
    fun update(slug: String, post: Post)
    fun delete(slug: String)
}
