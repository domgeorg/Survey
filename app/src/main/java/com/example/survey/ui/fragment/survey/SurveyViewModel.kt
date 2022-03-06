package com.example.survey.ui.fragment.survey

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.survey.data.SubmitData
import com.example.survey.data.SurveyData
import com.example.survey.model.error.Failure
import com.example.survey.model.question.Empty
import com.example.survey.model.question.SurveyUIModel
import com.example.survey.network.NetworkDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.properties.Delegates

@HiltViewModel
class SurveyViewModel @Inject constructor(
    private val networkDataSource: NetworkDataSource
) : ViewModel() {

    private val _loading: MutableLiveData<Boolean> = MutableLiveData()
    val loading: LiveData<Boolean>
        get() = _loading

    private val _error: MutableLiveData<Failure> = MutableLiveData()
    val error: LiveData<Failure>
        get() = _error

    private val _questions: MutableLiveData<List<SurveyUIModel>> = MutableLiveData()
    val questions: LiveData<List<SurveyUIModel>>
        get() = _questions

    private val _submittedQuestions: MutableLiveData<Int> = MutableLiveData()
    val submittedQuestions: LiveData<Int>
        get() = _submittedQuestions

    private val _submitted: MutableLiveData<Empty> = MutableLiveData()
    val submitted: LiveData<Empty>
        get() = _submitted

    private val _surveyEnds: MutableLiveData<Empty> = MutableLiveData()
    val surveyEnds: LiveData<Empty>
        get() = _surveyEnds

    private var submittedCounter by Delegates.observable(0) { _, _, newValue ->
        _submittedQuestions.postValue(newValue)
        if (newValue == surveyModels.values.count()) {
            _surveyEnds.postValue(Empty)
        }
    }
    private var surveyModels: MutableMap<Int, SurveyUIModel> = linkedMapOf()

    private var pendingRequest: Pair<SurveyData, String>? = null

    fun start() {
        _submittedQuestions.postValue(0)
        getSurveyData()
    }

    fun submitQuestion(data: SurveyData, answer: String) {
        if (answer.isEmpty()) {
            _error.postValue(Failure.InvalidInput)
            return
        }
        _loading.postValue(true)
        pendingRequest = Pair(data, answer)
        viewModelScope.launch {
            networkDataSource.submitAnswer(SubmitData(data.id, answer)).fold(
                onSuccess = {
                    pendingRequest = null
                    _submitted.postValue(Empty)
                    submittedCounter++
                    surveyModels.replace(data.id, SurveyUIModel(data, answer, isSubmitted = true))
                    val result = ArrayList<SurveyUIModel>()
                    surveyModels.values.forEach {
                        result.add(it.copy())
                    }
                    _questions.postValue(result)

                },
                onFailure = { _error.postValue(Failure.SubmitError) }
            )
            _loading.postValue(false)
        }
    }

    fun retry() {
        pendingRequest?.also {
            submitQuestion(it.first, it.second)
        }
    }

    private fun getSurveyData() {
        _loading.postValue(true)
        viewModelScope.launch {
            networkDataSource.getSurveyData().fold(
                onSuccess = { data ->
                    surveyModels = data.associateBy({ it.id }, { SurveyUIModel(it) }).toMutableMap()
                    _questions.postValue(surveyModels.values.toList())
                },
                onFailure = { _error.postValue(Failure.SurveyDataError) }
            )

            _loading.postValue(false)
        }
    }
}