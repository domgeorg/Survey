package com.georgiopoulos.core.domain.model.error.mapper

import com.georgiopoulos.core.domain.model.error.ErrorModel

internal interface ErrorMapper {

    fun mapError(exception: Throwable?): ErrorModel
}
