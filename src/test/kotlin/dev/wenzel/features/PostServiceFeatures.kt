package dev.wenzel.features

import dev.wenzel.domain.exceptions.DuplicatedSlugException
import dev.wenzel.domain.exceptions.InvalidSlugException
import dev.wenzel.domain.services.PostService
import dev.wenzel.domain.validators.PostValidator
import dev.wenzel.doubles.FakePostRepository
import dev.wenzel.util.createInvalidSlugDummyPost
import dev.wenzel.util.createValidDummyPost
import org.junit.jupiter.api.BeforeEach
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

    @BeforeEach
    fun setup() {
        postRepository.flush()
    }

    @Test
    fun `adding a new post makes it available to the service`() {
        val post = createValidDummyPost()

        postService.addNewPost(post)
        val actualPost = postService.getBySlug(post.slug)

        expectThat(actualPost).isEqualTo(post)
    }

    @Test
    fun `adding a post with a non snake cased slug will deny its creation`() {
        val invalidSlugPost = createInvalidSlugDummyPost()

        expectCatching { postService.addNewPost(invalidSlugPost) }
            .isFailure()
            .isA<InvalidSlugException>()
    }

    @Test
    fun `adding a post with a duplicated slug will deny its creation`() {
        val post = createValidDummyPost()
        postService.addNewPost(post)

        expectCatching { postService.addNewPost(post) }
            .isFailure()
            .isA<DuplicatedSlugException>()
    }
}