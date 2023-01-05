package dev.wenzel.util

import dev.wenzel.domain.model.Post
import java.time.LocalDate
import java.time.Month

fun createValidDummyPost(): Post = Post(
    slug = "valid-slug",
    title = "Valid Title",
    createdAt = LocalDate.of(2023, Month.JANUARY, 1),
    body = "This is a valid blog post body :)",
    tags = listOf()
)