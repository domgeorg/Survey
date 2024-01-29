package com.georgiopoulos.core.data.model.answer

import com.georgiopoulos.core.network.request.SubmitAnswerRequest

internal interface AnswerRequestBuilder {

    fun build(questionId: Int, answer: String): SubmitAnswerRequest
}