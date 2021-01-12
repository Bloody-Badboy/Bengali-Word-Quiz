/*
 * Copyright 2021 Arpan Sarkar
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.arpan.bengali.quiz.ui.quiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import dev.arpan.bengali.quiz.R
import dev.arpan.bengali.quiz.databinding.FragmentQuizBinding
import dev.arpan.bengali.quiz.ui.NavigationDestinationFragment
import dev.arpan.bengali.quiz.ui.utils.EventObserver

@AndroidEntryPoint
class QuizFragment @JvmOverloads constructor(
    factory: ViewModelProvider.Factory? = null
) : NavigationDestinationFragment() {

    val viewModel: QuizViewModel by viewModels(factoryProducer = factory?.let { { factory } })

    private lateinit var binding: FragmentQuizBinding
    private val args: QuizFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQuizBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.numberOfWords = args.numberOfWords
        viewModel.numberOfOptions = args.numberOfOptions
        viewModel.isEnglishToBengali = args.englishToBengali

        initAdapter()

        viewModel.currentWordIndex.observe(
            viewLifecycleOwner,
            {
                viewModel.setToolbarTitle(
                    getString(
                        R.string.quiz_toolbar_title,
                        it,
                        args.numberOfWords
                    )
                )
            }
        )

        viewModel.showFinishedDialog.observe(
            viewLifecycleOwner,
            EventObserver {
                MaterialAlertDialogBuilder(requireContext()).apply {
                    setMessage("Your score: ${viewModel.currentScore}/${args.numberOfWords}")
                    setCancelable(false)
                    setPositiveButton(android.R.string.ok) { _, _ ->
                        findNavController().navigateUp()
                    }
                }.show()
            }
        )
        viewModel.getQuestion()

        return binding.root
    }

    private fun initAdapter() {
        val optionAdapter = QuestionOptionsAdapter {
            if (it.correctOption) {
                viewModel.currentScore++
            }
            binding.btnNext.isEnabled = true
        }
        with(binding.recyclerView) {
            layoutManager = LinearLayoutManager(context)
            adapter = optionAdapter
            (itemAnimator as DefaultItemAnimator).run {
                supportsChangeAnimations = true
                addDuration = 250L
                moveDuration = 250L
                changeDuration = 250L
                removeDuration = 250L
            }
        }

        viewModel.quizWord.observe(
            viewLifecycleOwner,
            {
                binding.btnNext.isEnabled = false
                optionAdapter.data = it.options
            }
        )
    }
}
