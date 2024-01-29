package com.georgiopoulos.core.domain.di

import com.georgiopoulos.core.domain.model.error.mapper.ErrorMapper
import com.georgiopoulos.core.domain.model.error.mapper.ErrorMapperImpl
import com.georgiopoulos.core.domain.model.question.mapper.QuestionMapper
import com.georgiopoulos.core.domain.model.question.mapper.QuestionMapperImpl
import com.georgiopoulos.core.domain.usecase.load.LoadSurveyUseCase
import com.georgiopoulos.core.domain.usecase.load.LoadSurveyUseCaseImpl
import com.georgiopoulos.core.domain.usecase.submit.SubmitAnswerUseCase
import com.georgiopoulos.core.domain.usecase.submit.SubmitAnswerUseCaseImpl
import com.georgiopoulos.core.domain.usecase.validate.AnswerValidationUseCase
import com.georgiopoulos.core.domain.usecase.validate.AnswerValidationUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface DomainModule {

    @Binds
    fun bindsErrorMapper(
        errorMapperImpl: ErrorMapperImpl,
    ): ErrorMapper

    @Binds
    fun bindsQuestionMapper(
        questionMapperImpl: QuestionMapperImpl,
    ): QuestionMapper

    @Binds
    fun bindsLoadSurveyUseCase(
        loadSurveyUseCaseImpl: LoadSurveyUseCaseImpl,
    ): LoadSurveyUseCase

    @Binds
    fun bindsSubmitAnswerUseCase(
        submitAnswerUseCaseImpl: SubmitAnswerUseCaseImpl,
    ): SubmitAnswerUseCase

    @Binds
    fun bindAnswerValidationUseCase(
        answerValidationUseCaseImpl: AnswerValidationUseCaseImpl,
    ): AnswerValidationUseCase
}
