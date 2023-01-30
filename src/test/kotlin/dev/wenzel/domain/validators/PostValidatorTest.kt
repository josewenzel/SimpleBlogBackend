package dev.wenzel.domain.validators

import dev.wenzel.domain.exceptions.InvalidSlugException
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.TestFactory
import strikt.api.expectCatching
import strikt.api.expectThat
import strikt.assertions.isA
import strikt.assertions.isFailure
import strikt.assertions.isTrue

class PostValidatorTest {
    private val postValidator = PostValidator()

    private val validSlugs = listOf(
        "a", "a-c"
    )

    private val invalidSlugs = listOf(
        "a p", "a_p", "a_p-c", "a--c"
    )

    @TestFactory
    fun validSlugValidation() = validSlugs.map {
        dynamicTest("'$it' returns") {
            expectThat(postValidator.validateSlug(it)).isTrue()
        }
    }

    @TestFactory
    fun invalidSlugValidation() = invalidSlugs.map {
        dynamicTest("'$it' throws an invalid slug exception") {
            expectCatching { postValidator.validateSlug(it) }
                .isFailure()
                .isA<InvalidSlugException>()
        }
    }
}