package com.georgiopoulos.core.domain.model.question

import javax.annotation.concurrent.Immutable

@Immutable
data class QuestionModel(
    val question: String,
    val answer: String = "",
    val pendingAnswer: String = "",
)
