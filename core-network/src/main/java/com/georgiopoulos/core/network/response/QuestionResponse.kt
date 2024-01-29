package com.georgiopoulos.core.network.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class QuestionResponse(
    val id: Int,
    val question: String,
)
