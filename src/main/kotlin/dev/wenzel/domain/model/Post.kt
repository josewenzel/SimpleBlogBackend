package dev.wenzel.domain.model

import java.time.LocalDate

data class Post(
    val slug: String,
    val title: String,
    val createdAt: LocalDate,
    val body: String,
    val tags: List<Tag>
)