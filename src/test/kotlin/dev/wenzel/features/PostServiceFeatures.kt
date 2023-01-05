package dev.wenzel.features

import dev.wenzel.domain.model.Post
import dev.wenzel.domain.ports.PostRepository
import dev.wenzel.domain.services.PostService
import dev.wenzel.doubles.FakePostRepository
import dev.wenzel.util.createValidDummyPost
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.contains

class PostServiceFeatures {
    private val post: Post = createValidDummyPost()

    @Test
    fun `adding a new post makes it available to the service`() {
        val postRepository: PostRepository = FakePostRepository()
        val postService = PostService(postRepository)

        postService.addNewPost(post)
        val postListing: List<Post> = postService.getAllPosts()

        expectThat(postListing).contains(post)
    }
}