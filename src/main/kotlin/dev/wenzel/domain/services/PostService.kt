package dev.wenzel.domain.services

import dev.wenzel.domain.exceptions.DuplicatedSlugException
import dev.wenzel.domain.exceptions.PostDoesNotExistException
import dev.wenzel.domain.model.Post
import dev.wenzel.domain.ports.PostRepository
import dev.wenzel.domain.validators.PostValidator

class PostService(private val postRepository: PostRepository, private val postValidator: PostValidator) {
    fun addNewPost(post: Post) {
        if (postAlreadyExists(post))
            throw DuplicatedSlugException(post.slug)
        postValidator.validateSlug(post.slug)
        postRepository.add(post)
    }

    fun getBySlug(slug: String): Post? = postRepository.getBySlug(slug)

    fun updatePost(slugToBeUpdated: String, updatedPost: Post) {
        if (!postAlreadyExists(updatedPost))
            throw PostDoesNotExistException(updatedPost.slug)
        postRepository.update(slugToBeUpdated, updatedPost)
    }

    private fun postAlreadyExists(post: Post) = postRepository.getBySlug(post.slug) != null
}
