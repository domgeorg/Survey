package com.georgiopoulos.core.data.model.question.mapper

import com.georgiopoulos.core.data.model.question.QuestionDataModel
import com.georgiopoulos.core.network.response.QuestionResponse

internal interface QuestionMapper {
    fun map(questions: List<QuestionResponse>): List<QuestionDataModel>
}