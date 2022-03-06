package com.example.survey.ui.fragment.survey

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.survey.data.SurveyData
import com.example.survey.model.error.Failure
import com.example.survey.model.question.Empty
import com.example.survey.model.question.SurveyUIModel
import com.example.survey.network.NetworkDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.lang.reflect.Field
import kotlin.reflect.KMutableProperty

@OptIn(ExperimentalCoroutinesApi::class)
class SurveyViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: SurveyViewModel

    @Mock
    private lateinit var mockNetworkDataSource: NetworkDataSource

    private val dummySurveyData = mutableListOf<SurveyData>().apply {
        add(SurveyData(1, "What is your favourite colour?"))
        add(SurveyData(2, "What is your favourite food?"))
        add(SurveyData(3, "What is your favourite country?"))
        add(SurveyData(4, "What is your favourite sport?"))
        add(SurveyData(5, "What is your favourite team?"))
    }

    private val resultUIData = dummySurveyData.map { SurveyUIModel(it) }

    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        viewModel = SurveyViewModel(mockNetworkDataSource)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    //region start
    @Test
    fun `given success response when fetch survey data then should post 0 submitted questions and the survey data`() =
        runBlocking {
            `when`(mockNetworkDataSource.getSurveyData()).thenReturn(Result.success(dummySurveyData))

            viewModel.start()

            viewModel.submittedQuestions.observeForever {
                Assert.assertEquals(it, 0)
            }

            viewModel.questions.observeForever {
                Assert.assertEquals(it, resultUIData)
            }
        }

    @Test
    fun `given error response when fetch survey data then should post 0 submitted questions and the error Failure SurveyDataError`() =
        runBlocking {
            `when`(mockNetworkDataSource.getSurveyData()).thenReturn(Result.failure(Exception("Test Exception")))

            viewModel.start()

            viewModel.submittedQuestions.observeForever {
                Assert.assertEquals(it, 0)
            }

            viewModel.error.observeForever {
                Assert.assertEquals(it, Failure.SurveyDataError)
            }
        }
    //endregion

    //region submitQuestion
    @Test
    fun `given invalid answer when submitQuestion then should post error Failure InvalidInput`() {
        viewModel.submitQuestion(dummySurveyData[0], "")

        viewModel.error.observeForever {
            Assert.assertEquals(it, Failure.InvalidInput)
        }
    }

    @Test
    fun `given error response when submitQuestion then should post error Failure SubmitError`() =
        runBlocking {
            `when`(mockNetworkDataSource.submitAnswer(any())).thenReturn(Result.failure(Exception("Test Exception")))

            viewModel.submitQuestion(dummySurveyData[0], "Black")

            viewModel.error.observeForever {
                Assert.assertEquals(it, Failure.SubmitError)
            }
        }

    @Test
    fun `given success response when submitQuestion then should post 1 submitted Questions and submitted and updated survey data`() =
        runBlocking {
            val result = mutableListOf<SurveyUIModel>().apply {
                addAll(resultUIData)
                removeAt(0)
                add(0, SurveyUIModel(dummySurveyData[0], "Black", true))
            }

            //Fill survey data
            `when`(mockNetworkDataSource.getSurveyData()).thenReturn(Result.success(dummySurveyData))
            viewModel.start()

            `when`(mockNetworkDataSource.submitAnswer(any())).thenReturn(Result.success(Unit))

            viewModel.submitQuestion(dummySurveyData[0], "Black")

            viewModel.submittedQuestions.observeForever {
                Assert.assertEquals(it, 1)
            }

            viewModel.submitted.observeForever {
                Assert.assertEquals(it, Empty)
            }

            viewModel.questions.observeForever {
                Assert.assertEquals(it, result)
            }
        }
    //endregion

    //region retry
    @Test
    fun `given error response when retry then should post error Failure SubmitError`() =
        runBlocking {
            //Using reflection
            val pendingRequest: Field = viewModel.javaClass.getDeclaredField("pendingRequest")
            pendingRequest.isAccessible = true
            pendingRequest.set(viewModel, Pair(dummySurveyData[0], "Black"))

            `when`(mockNetworkDataSource.submitAnswer(any())).thenReturn(Result.failure(Exception("Test Exception")))

            viewModel.retry()

            viewModel.error.observeForever {
                Assert.assertEquals(it, Failure.SubmitError)
            }
        }

    @Test
    fun `given success response when retry then should post 1 submitted Questions and submitted and updated survey data`() =
        runBlocking {
            //Using reflection
            val pendingRequest: Field = viewModel.javaClass.getDeclaredField("pendingRequest")
            pendingRequest.isAccessible = true
            pendingRequest.set(viewModel, Pair(dummySurveyData[0], "Black"))

            val result = mutableListOf<SurveyUIModel>().apply {
                addAll(resultUIData)
                removeAt(0)
                add(0, SurveyUIModel(dummySurveyData[0], "Black", true))
            }

            //Fill survey data
            `when`(mockNetworkDataSource.getSurveyData()).thenReturn(Result.success(dummySurveyData))
            viewModel.start()

            `when`(mockNetworkDataSource.submitAnswer(any())).thenReturn(Result.success(Unit))

            viewModel.retry()

            viewModel.submittedQuestions.observeForever {
                Assert.assertEquals(it, 1)
            }

            viewModel.submitted.observeForever {
                Assert.assertEquals(it, Empty)
            }

            viewModel.questions.observeForever {
                Assert.assertEquals(it, result)
            }
        }

    //endregion

    @Test
    fun `given submitted questions counter reaches end then show end of survey`() =
        runBlocking {
            //Fill survey data
            `when`(mockNetworkDataSource.getSurveyData()).thenReturn(Result.success(dummySurveyData))
            viewModel.start()

            val setter = viewModel.javaClass.declaredMethods.first { it.name.contains("setSubmittedCounter") }
            setter.isAccessible = true
            setter.invoke(viewModel, 5)

            viewModel.surveyEnds.observeForever {
                Assert.assertEquals(it, Empty)
            }
        }

    /**
     * Returns Mockito.any() as nullable type to avoid java.lang.IllegalStateException when
     * null is returned.
     */
    private fun <T> any(): T = Mockito.any<T>()
}