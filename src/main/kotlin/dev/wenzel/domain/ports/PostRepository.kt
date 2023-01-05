package dev.wenzel.domain.ports

import dev.wenzel.domain.model.Post

interface PostRepository {
    fun add(post: Post)
    fun getAll(): List<Post>
}
