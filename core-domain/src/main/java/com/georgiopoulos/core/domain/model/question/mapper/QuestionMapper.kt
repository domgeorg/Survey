package com.georgiopoulos.core.domain.model.question.mapper

import com.georgiopoulos.core.data.model.question.QuestionDataModel
import com.georgiopoulos.core.domain.model.question.QuestionModel

internal interface QuestionMapper {
    fun map(data: List<QuestionDataModel>): Map<Int, QuestionModel>
}