package dev.wenzel.doubles

import dev.wenzel.domain.model.Post
import dev.wenzel.domain.ports.PostRepository

class FakePostRepository : PostRepository {
    private val store = HashMap<String, Post>()

    override fun add(post: Post) {
        store[post.slug] = post
    }

    override fun getBySlug(slug: String): Post? = store[slug]

    override fun update(slug: String, post: Post) {
        store[post.slug] = post
    }

    fun flush() {
        store.clear()
    }
}
