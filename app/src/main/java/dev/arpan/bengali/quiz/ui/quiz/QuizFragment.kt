package dev.arpan.bengali.quiz.ui.quiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
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
class QuizFragment : NavigationDestinationFragment() {

    private val viewModel: QuizViewModel by viewModels()

    private lateinit var binding: FragmentQuizBinding
    private val args: QuizFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQuizBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.numberOfWords = args.numberOfWords
        viewModel.numberOfOptions = args.numberOfOptions
        viewModel.isEnglishToBengali = args.englishToBengali

        initAdapter()

        viewModel.currentWordIndex.observe(viewLifecycleOwner, {
            viewModel.setToolbarTitle(
                getString(
                    R.string.quiz_toolbar_title,
                    it,
                    args.numberOfWords
                )
            )
        })

        viewModel.showFinishedDialog.observe(viewLifecycleOwner, EventObserver {
            MaterialAlertDialogBuilder(requireContext()).apply {
                setMessage("Your score: ${viewModel.currentScore}/${args.numberOfWords}")
                setCancelable(false)
                setPositiveButton(android.R.string.ok) { _, _ ->
                    findNavController().navigateUp()
                }
            }.show()
        })
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

        viewModel.quizWord.observe(viewLifecycleOwner, {
            binding.btnNext.isEnabled = false
            optionAdapter.data = it.options
        })
    }
}