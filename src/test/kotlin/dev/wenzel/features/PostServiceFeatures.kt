package dev.wenzel.features

import dev.wenzel.domain.exceptions.InvalidSlugException
import dev.wenzel.domain.model.Post
import dev.wenzel.domain.services.PostService
import dev.wenzel.domain.validators.PostValidator
import dev.wenzel.doubles.FakePostRepository
import dev.wenzel.util.createInvalidSlugDummyPost
import dev.wenzel.util.createValidDummyPost
import org.junit.jupiter.api.Test
import strikt.api.expectCatching
import strikt.api.expectThat
import strikt.assertions.isA
import strikt.assertions.isEqualTo
import strikt.assertions.isFailure

class PostServiceFeatures {
    private val postRepository = FakePostRepository()
    private val postValidator = PostValidator()

    private val postService = PostService(postRepository, postValidator)

    @Test
    fun `adding a new post makes it available to the service`() {
        val post: Post = createValidDummyPost()
        postService.addNewPost(post)
        val postListing = postService.getBySlug(post.slug)

        expectThat(postListing).isEqualTo(post)
    }

    @Test
    fun `adding a post with a non snake cased slug will deny its creation`() {
        val invalidSlugPost = createInvalidSlugDummyPost()

        expectCatching { postService.addNewPost(invalidSlugPost) }
            .isFailure()
            .isA<InvalidSlugException>()
    }
}