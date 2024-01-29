package com.georgiopoulos.core.data.model.answer

import com.georgiopoulos.core.network.request.SubmitAnswerRequest
import javax.inject.Inject

internal class AnswerRequestBuilderImpl @Inject constructor(): AnswerRequestBuilder {
    override fun build(questionId: Int, answer: String) = SubmitAnswerRequest(questionId, answer)
}