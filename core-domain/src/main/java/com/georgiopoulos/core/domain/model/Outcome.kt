package com.georgiopoulos.core.domain.model

import com.georgiopoulos.core.domain.model.error.ErrorModel

sealed class Outcome<out T : Any> {
    data class Success<out T : Any>(val value: T) : Outcome<T>()
    data class Error(val value: ErrorModel) : Outcome<Nothing>()
}
