package dev.wenzel.domain.services

import dev.wenzel.domain.exceptions.DuplicatedSlugException
import dev.wenzel.domain.exceptions.InvalidSlugException
import dev.wenzel.domain.model.Post
import dev.wenzel.domain.ports.PostRepository
import dev.wenzel.domain.validators.PostValidator

class PostService(private val postRepository: PostRepository, private val postValidator: PostValidator) {
    fun addNewPost(post: Post) {
        if (!postValidator.isValidSlug(post.slug))
            throw InvalidSlugException("'${post.slug}' is not a valid slug'}")
        if (slugAlreadyExists(post))
            throw DuplicatedSlugException("'${post.slug}' slug already exists")
        postRepository.add(post)
    }

    private fun slugAlreadyExists(post: Post) = postRepository.getBySlug(post.slug) != null

    fun getBySlug(slug: String): Post? = postRepository.getBySlug(slug)
}
