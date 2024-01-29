package com.georgiopoulos.core.network.request

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SubmitAnswerRequest(
    val id: Int,
    val answer: String,
)
