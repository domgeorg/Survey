package com.georgiopoulos.core.data.model.question.mapper

import com.georgiopoulos.core.data.model.question.QuestionDataModel
import com.georgiopoulos.core.network.response.QuestionResponse
import org.junit.Assert.assertEquals
import org.junit.Test

class QuestionMapperImplTest {

    private val questionMapperImpl = QuestionMapperImpl()

    @Test
    fun `Given list of QuestionResponse when map then return list of QuestionDataModel`() {
        // Given
        val questionResponses = listOf(
            QuestionResponse(1, "What is your favorite colour?"),
            QuestionResponse(2, "What is your favorite food?")
        )

        val expectedQuestionDataModels = listOf(
            QuestionDataModel(1, "What is your favorite colour?"),
            QuestionDataModel(2, "What is your favorite food?")
        )

        // When
        val result = questionMapperImpl.map(questionResponses)

        // Then
        assertEquals(expectedQuestionDataModels, result)
    }
}