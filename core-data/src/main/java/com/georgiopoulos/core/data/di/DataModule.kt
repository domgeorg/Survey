package com.georgiopoulos.core.data.di

import com.georgiopoulos.core.data.model.answer.AnswerRequestBuilder
import com.georgiopoulos.core.data.model.answer.AnswerRequestBuilderImpl
import com.georgiopoulos.core.data.model.question.mapper.QuestionMapper
import com.georgiopoulos.core.data.model.question.mapper.QuestionMapperImpl
import com.georgiopoulos.core.data.repository.SurveyRepository
import com.georgiopoulos.core.data.repository.SurveyRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface DataModule {

    @Binds
    fun bindsAnswerRequestBuilder(
        answerRequestBuilderImpl: AnswerRequestBuilderImpl,
    ): AnswerRequestBuilder

    @Binds
    fun bindsQuestionMapper(
        questionMapperImpl: QuestionMapperImpl,
    ): QuestionMapper

    @Binds
    fun bindsSurveyRepository(
        surveyRepositoryImpl: SurveyRepositoryImpl,
    ): SurveyRepository
}
