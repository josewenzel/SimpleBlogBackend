package dev.wenzel.domain.services

import dev.wenzel.domain.model.Post
import dev.wenzel.domain.ports.PostRepository

class PostService(postRepository: PostRepository) {
    fun addNewPost(post: Post) {
        TODO("Not yet implemented")
    }

    fun getAllPosts(): List<Post> {
        TODO("Not yet implemented")
    }
}
