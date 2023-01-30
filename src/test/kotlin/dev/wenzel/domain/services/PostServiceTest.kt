package dev.wenzel.domain.services

import dev.wenzel.domain.exceptions.DuplicatedSlugException
import dev.wenzel.domain.exceptions.PostDoesNotExistException
import dev.wenzel.domain.ports.PostRepository
import dev.wenzel.domain.validators.PostValidator
import dev.wenzel.util.createValidDummyPost
import io.mockk.every
import io.mockk.spyk
import io.mockk.verify
import org.junit.jupiter.api.Test
import strikt.api.expectCatching
import strikt.api.expectThat
import strikt.assertions.isA
import strikt.assertions.isEqualTo
import strikt.assertions.isFailure

class PostServiceTest {
    private val postValidator = spyk<PostValidator>()
    private val postRepository = spyk<PostRepository>()

    private val postService = PostService(postRepository, postValidator)

    @Test
    fun `requests the repository to add new valid post`() {
        val post = createValidDummyPost()
        every { postValidator.validateSlug(post.slug) } returns true

        postService.addNewPost(post)

        verify { postRepository.add(post) }
    }

    @Test
    fun `gets the post when asked for it if it exists`() {
        val post = createValidDummyPost()
        every { postRepository.getBySlug(post.slug) } returns post

        val postBySlug = postService.getBySlug(post.slug)

        expectThat(post).isEqualTo(postBySlug)
    }

    @Test
    fun `doesn't allow to create a post with a slug that exists`() {
        val post = createValidDummyPost()
        every { postRepository.getBySlug(post.slug) } returns post

        expectCatching { postService.addNewPost(post) }
            .isFailure()
            .isA<DuplicatedSlugException>()
    }

    @Test
    fun `requests the repository to update an existing post`() {
        val post = createValidDummyPost()
        every { postRepository.getBySlug(post.slug) } returns post

        postService.updatePost(post.slug, post)

        verify { postRepository.update(post.slug, post) }
    }

    @Test
    fun `doesn't allow to update a post that does not exist`() {
        val post = createValidDummyPost()
        every { postRepository.getBySlug(post.slug) } returns null

        expectCatching { postService.updatePost(post.slug, post) }
            .isFailure()
            .isA<PostDoesNotExistException>()
    }
}