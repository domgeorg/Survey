package com.example.survey.ui.fragment.survey

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.survey.R
import com.example.survey.common.custom.layout_manager.HorizontalLayoutManager
import com.example.survey.common.view_binding.viewBinding
import com.example.survey.databinding.FragmentSurveyBinding
import com.example.survey.model.error.Failure
import com.example.survey.model.error.Failure.SubmitError
import com.example.survey.model.error.Failure.SurveyDataError
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SurveyFragment : Fragment(R.layout.fragment_survey) {

    private val binding by viewBinding(FragmentSurveyBinding::bind)

    private val viewModel: SurveyViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding.recyclerview) {
            layoutManager = HorizontalLayoutManager(context)
            adapter = SurveyAdapter(
                onSubmit = { data, answer -> viewModel.submitQuestion(data, answer) },
                onNavigate = { position -> smoothScrollToPosition(position) }
            )
        }

        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            binding.loadingComponent.loading.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            when (error) {
                SubmitError -> showSnackBar(
                    R.color.red,
                    R.string.error_submission,
                    R.string.retry
                ) { viewModel.retry() }

                SurveyDataError -> binding.errorComponent.error.visibility = View.VISIBLE

                Failure.InvalidInput -> showSnackBar(R.color.red, R.string.error_invalid_input)
            }
        }

        viewModel.submittedQuestions.observe(viewLifecycleOwner) {
            binding.questionsSubmitted.text = getString(R.string.questions_submitted, it)
        }

        viewModel.submitted.observe(viewLifecycleOwner) {
            showSnackBar(R.color.green, R.string.success_submission)
        }

        viewModel.questions.observe(viewLifecycleOwner) {
            (binding.recyclerview.adapter as SurveyAdapter).submitList(it)
        }

        viewModel.surveyEnds.observe(viewLifecycleOwner) {
            binding.endComponent.end.visibility = View.VISIBLE
        }

        viewModel.start()
    }

    private fun showSnackBar(
        backgroundColor: Int,
        message: Int,
        textButton: Int? = null,
        onClick: (() -> Unit)? = null
    ) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).also {
            it.view.background.colorFilter = PorterDuffColorFilter(
                ContextCompat.getColor(it.context, backgroundColor),
                PorterDuff.Mode.SRC_ATOP
            )

            if (textButton != null && onClick != null) {
                it.setActionTextColor(ContextCompat.getColor(it.context, R.color.white))
                it.setAction(textButton) { onClick.invoke() }
            }
        }.show()
    }
}