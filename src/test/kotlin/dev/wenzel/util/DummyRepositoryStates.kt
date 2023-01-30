package dev.wenzel.util

import dev.wenzel.domain.model.Post
import dev.wenzel.doubles.FakePostRepository

fun createRepoWithOnePost(postRepository: FakePostRepository): Post {
    val post = createValidDummyPost()
    postRepository.add(post)
    return post
}