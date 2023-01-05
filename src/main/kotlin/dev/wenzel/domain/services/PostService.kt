package dev.wenzel.domain.services

import dev.wenzel.domain.model.Post
import dev.wenzel.domain.ports.PostRepository
import dev.wenzel.domain.validators.PostValidator

class PostService(private val postRepository: PostRepository, val postValidator: PostValidator) {
    fun addNewPost(post: Post) {
        postRepository.add(post)
    }

    fun getBySlug(slug: String): Post = postRepository.getBySlug(slug)
}
