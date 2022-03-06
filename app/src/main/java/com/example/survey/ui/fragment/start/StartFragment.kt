package com.example.survey.ui.fragment.start

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.survey.R
import com.example.survey.common.view_binding.viewBinding
import com.example.survey.databinding.FragmentStartBinding

class StartFragment : Fragment(R.layout.fragment_start) {

    private val binding by viewBinding(FragmentStartBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.startButton.setOnClickListener {
            val action = StartFragmentDirections.actionSurveyFragment()
            findNavController().navigate(action)
        }
    }
}