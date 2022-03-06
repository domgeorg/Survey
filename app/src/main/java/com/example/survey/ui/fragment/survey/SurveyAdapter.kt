package com.example.survey.ui.fragment.survey

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.survey.R
import com.example.survey.common.extensions.hideKeyboard
import com.example.survey.data.SurveyData
import com.example.survey.databinding.LayoutQuestionBinding
import com.example.survey.model.question.SurveyUIModel

class SurveyAdapter(
    private val onSubmit: (SurveyData, String) -> Unit,
    private val onNavigate: (Int) -> Unit
) : ListAdapter<SurveyUIModel, SurveyAdapter.ViewHolder>(SurveyDataDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            LayoutQuestionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getItemViewType(position: Int): Int = position

    inner class ViewHolder(
        private val binding: LayoutQuestionBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private val resources = binding.root.context.resources

        fun bind(model: SurveyUIModel, position: Int) = with(binding) {
            val isFirst = position == 0
            val isLast = position + 1 == itemCount

            previous.isEnabled = !isFirst
            val previousColor = if (isFirst) R.color.grey else R.color.purple_500
            previous.setTextColor(resources.getColor(previousColor, null))
            previous.setOnClickListener { onNavigate.invoke(position - 1) }

            next.isEnabled = !isLast
            val nextColor = if (isLast) R.color.grey else R.color.purple_500
            next.setTextColor(resources.getColor(nextColor, null))
            next.setOnClickListener { onNavigate.invoke(position + 1) }

            question.text = model.surveyData.question

            model.answer?.also {
                answer.setText(it)
                answer.isEnabled = false
                submitButton.isEnabled = false
                submitButton.text = resources.getString(R.string.submitted)
            }
            answer.doAfterTextChanged {
                submitButton.isEnabled = it.toString().isNotEmpty() && !model.isSubmitted
            }

            questionNumber.text =
                resources.getString(R.string.question_number, position + 1, itemCount)
            submitButton.setOnClickListener {
                onSubmit.invoke(model.surveyData, answer.text.toString())
                answer.hideKeyboard()
            }
        }
    }
}

class SurveyDataDiffCallback : DiffUtil.ItemCallback<SurveyUIModel>() {
    override fun areItemsTheSame(oldItem: SurveyUIModel, newItem: SurveyUIModel): Boolean =
        oldItem.surveyData.id == newItem.surveyData.id

    override fun areContentsTheSame(oldItem: SurveyUIModel, newItem: SurveyUIModel): Boolean =
        oldItem.surveyData.question == newItem.surveyData.question
                && oldItem.answer == newItem.answer
                && oldItem.isSubmitted == newItem.isSubmitted
}