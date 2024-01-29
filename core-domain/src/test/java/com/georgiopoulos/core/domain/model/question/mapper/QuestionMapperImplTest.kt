package com.georgiopoulos.core.domain.model.question.mapper

import com.georgiopoulos.core.data.model.question.QuestionDataModel
import com.georgiopoulos.core.domain.model.question.QuestionModel
import org.junit.Assert.assertEquals
import org.junit.Test

class QuestionMapperImplTest {

    private val questionMapperImpl = QuestionMapperImpl()

    @Test
    fun `Given list of QuestionDataModel, When map, Then return map of QuestionModel`() {
        // Given
        val questionDataModels = listOf(
            QuestionDataModel(1, "What is your favorite colour?"),
            QuestionDataModel(2, "What is your favorite sport?")
        )

        val expectedQuestionModels = mapOf(
            1 to QuestionModel("What is your favorite colour?"),
            2 to QuestionModel("What is your favorite sport?")
        )

        // When
        val result = questionMapperImpl.map(questionDataModels)

        // Then
        assertEquals(expectedQuestionModels, result)
    }
}