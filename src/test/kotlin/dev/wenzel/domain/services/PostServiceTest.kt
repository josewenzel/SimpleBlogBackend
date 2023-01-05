package dev.wenzel.domain.services

import dev.wenzel.domain.ports.PostRepository
import dev.wenzel.domain.validators.PostValidator
import dev.wenzel.util.createValidDummyPost
import io.mockk.every
import io.mockk.spyk
import io.mockk.verify
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class PostServiceTest {
    private val postValidator = spyk<PostValidator>()
    private val postRepository = spyk<PostRepository>()

    private val postService = PostService(postRepository, postValidator)

    @Test
    fun `requests the repository to add new valid post`() {
        val post = createValidDummyPost()
        every { postValidator.isValidMarkdown(post.body) } returns true

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
}