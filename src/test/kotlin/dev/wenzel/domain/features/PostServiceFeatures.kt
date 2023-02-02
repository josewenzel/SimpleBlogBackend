package dev.wenzel.domain.features

import dev.wenzel.domain.exceptions.DuplicatedSlugException
import dev.wenzel.domain.exceptions.InvalidSlugException
import dev.wenzel.domain.exceptions.PostDoesNotExistException
import dev.wenzel.domain.services.PostService
import dev.wenzel.domain.validators.PostValidator
import dev.wenzel.doubles.FakePostRepository
import dev.wenzel.util.createInvalidSlugDummyPost
import dev.wenzel.util.createRepoWithOnePost
import dev.wenzel.util.createValidDummyPost
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
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

    @Nested
    inner class AddingAPost {
        @Test
        fun `makes it available to the service`() {
            val post = createValidDummyPost()

            postService.addNewPost(post)

            val actualPost = postService.getBySlug(post.slug)
            expectThat(actualPost).isEqualTo(post)
        }

        @Test
        fun `with a non snake cased slug will deny its creation`() {
            val invalidSlugPost = createInvalidSlugDummyPost()

            expectCatching { postService.addNewPost(invalidSlugPost) }
                .isFailure()
                .isA<InvalidSlugException>()
        }

        @Test
        fun `with a duplicated slug will deny its creation`() {
            val post = createValidDummyPost()
            postService.addNewPost(post)

            expectCatching { postService.addNewPost(post) }
                .isFailure()
                .isA<DuplicatedSlugException>()
        }
    }

    @Nested
    inner class UpdatingAPost {
        @Test
        fun `removes old post and adds new one`() {
            val post = createRepoWithOnePost(postRepository)
            val updatedPost = post.copy(body = "This is a valid new blog post body :)")

            postService.updatePost(
                slugToBeUpdated = post.slug,
                updatedPost = updatedPost
            )

            val actualPost = postService.getBySlug(post.slug)
            expectThat(actualPost?.body).isEqualTo("This is a valid new blog post body :)")
        }

        @Test
        fun `for a non existent slug will deny it's modification`() {
            val post = createValidDummyPost()

            expectCatching { postService.updatePost(post.slug, post) }
                .isFailure()
                .isA<PostDoesNotExistException>()
        }
    }

    @Nested
    inner class RemovingAPost {
        @Test
        fun `makes it no longer available to the system`() {
            val post = createRepoWithOnePost(postRepository)

            postService.deletePost(post.slug)

            val actualPost = postService.getBySlug(post.slug)
            expectThat(actualPost).isEqualTo(null)
        }
    }
}