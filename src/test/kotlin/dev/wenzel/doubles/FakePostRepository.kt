package dev.wenzel.doubles

import dev.wenzel.domain.model.Post
import dev.wenzel.domain.ports.PostRepository

class FakePostRepository : PostRepository {
    override fun add(post: Post) {
        TODO("Not yet implemented")
    }

    override fun getAll(): List<Post> {
        TODO("Not yet implemented")
    }
}
