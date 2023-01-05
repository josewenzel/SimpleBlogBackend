package dev.wenzel.features

import dev.wenzel.domain.model.Post
import dev.wenzel.domain.services.PostService
import dev.wenzel.domain.validators.PostValidator
import dev.wenzel.doubles.FakePostRepository
import dev.wenzel.util.createValidDummyPost
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class PostServiceFeatures {
    private val post: Post = createValidDummyPost()
    private val postRepository = FakePostRepository()
    private val postValidator = PostValidator()

    @Test
    fun `adding a new post makes it available to the service`() {
        val postService = PostService(postRepository, postValidator)

        postService.addNewPost(post)
        val postListing = postService.getBySlug(post.slug)

        expectThat(postListing).isEqualTo(post)
    }
}