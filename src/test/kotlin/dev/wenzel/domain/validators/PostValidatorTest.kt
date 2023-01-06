package dev.wenzel.domain.validators

import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.TestFactory
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class PostValidatorTest {
    private val postValidator = PostValidator()

    private val testSlugs = listOf(
        "a" to true,
        "a p" to false,
        "a_p" to false,
        "a_p-c" to false,
        "a--c" to false,
        "a-c" to true,
    )

    @TestFactory
    fun validateSlug() =
        testSlugs.map { (slug, expected) ->
            dynamicTest("'$slug' is $expected") {
                expectThat(postValidator.isValidSlug(slug)).isEqualTo(expected)
            }
        }
}