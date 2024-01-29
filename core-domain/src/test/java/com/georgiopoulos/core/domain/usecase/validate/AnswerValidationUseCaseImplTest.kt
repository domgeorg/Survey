package com.georgiopoulos.core.domain.usecase.validate

import com.georgiopoulos.core.domain.model.answer.AnswerStatus
import com.georgiopoulos.core.domain.model.answer.InvalidReason
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class AnswerValidationUseCaseImplTest {

    private lateinit var answerValidationUseCase: AnswerValidationUseCase

    @Before
    fun setUp() {
        answerValidationUseCase = AnswerValidationUseCaseImpl()
    }

    @Test
    fun `Given an empty answer, When validateAnswer is called, Then it should return Invalid with Empty reason`() {
        val result = answerValidationUseCase.validateAnswer("")
        assertEquals(AnswerStatus.Invalid(InvalidReason.Empty), result)
    }

    @Test
    fun `Given a blank answer, When validateAnswer is called, Then it should return Invalid with Empty reason`() {
        val result = answerValidationUseCase.validateAnswer("    ")
        assertEquals(AnswerStatus.Invalid(InvalidReason.Empty), result)
    }

    @Test
    fun `Given an answer exceeding character limit, When validateAnswer is called, Then it should return Invalid with MaxCharacters reason`() {
        val longAnswer = "a".repeat(256)
        val result = answerValidationUseCase.validateAnswer(longAnswer)
        assertEquals(AnswerStatus.Invalid(InvalidReason.MaxCharacters), result)
    }

    @Test
    fun `Given an answer containing symbols, When validateAnswer is called, Then it should return Invalid with ContainsSymbols reason`() {
        val result = answerValidationUseCase.validateAnswer("Answer with symbols: !@#$%^&*()")
        assertEquals(AnswerStatus.Invalid(InvalidReason.ContainsSymbols), result)
    }

    @Test
    fun `Given a valid answer, When validateAnswer is called, Then it should return Valid`() {
        val result = answerValidationUseCase.validateAnswer("Valid answer")
        assertEquals(AnswerStatus.Valid, result)
    }
}