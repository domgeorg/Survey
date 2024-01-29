package com.georgiopoulos.core.domain.model.question.mapper

import com.georgiopoulos.core.data.model.question.QuestionDataModel
import com.georgiopoulos.core.domain.model.question.QuestionModel
import javax.inject.Inject

internal class QuestionMapperImpl @Inject constructor() : QuestionMapper {
    override fun map(data: List<QuestionDataModel>): Map<Int, QuestionModel> =
        data.associateBy(
            keySelector = { dataModel -> dataModel.id },
            valueTransform = { dataModel ->
                QuestionModel(
                    question = dataModel.question,
                )
            },
        )
}