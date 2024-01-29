package com.georgiopoulos.core.data.model.question.mapper

import com.georgiopoulos.core.data.model.question.QuestionDataModel
import com.georgiopoulos.core.network.response.QuestionResponse
import javax.inject.Inject

internal class QuestionMapperImpl @Inject constructor() : QuestionMapper {

    override fun map(questions: List<QuestionResponse>): List<QuestionDataModel> {
        return questions.map { question ->
            QuestionDataModel(
                id = question.id,
                question = question.question,
            )
        }
    }
}