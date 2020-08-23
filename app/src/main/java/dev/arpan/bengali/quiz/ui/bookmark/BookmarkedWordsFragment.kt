package dev.arpan.bengali.quiz.ui.bookmark

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import dev.arpan.bengali.quiz.databinding.FragmentBookmarkedWordsBinding
import dev.arpan.bengali.quiz.ui.NavigationDestinationFragment
import dev.arpan.bengali.quiz.ui.utils.SwipeToDeleteCallback
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BookmarkedWordsFragment : NavigationDestinationFragment() {

    private val viewModel: BookmarkedWordsViewModel by viewModels()
    private lateinit var binding: FragmentBookmarkedWordsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBookmarkedWordsBinding.inflate(inflater, container, false)

        val bookmarkedWordsAdapter = BookmarkedWordsAdapter()
        val itemTouchHelper = ItemTouchHelper(object : SwipeToDeleteCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val word = bookmarkedWordsAdapter.getWordAt(viewHolder.absoluteAdapterPosition)
                if (word != null) {
                    viewModel.bookmarkWord(word.id, false)
                }
            }
        })
        with(binding.rvWords) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = bookmarkedWordsAdapter
            itemTouchHelper.attachToRecyclerView(this)
        }

        bookmarkedWordsAdapter.addLoadStateListener { loadState ->
            binding.rvWords.isVisible = loadState.source.refresh is LoadState.NotLoading
            binding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading
        }

        lifecycleScope.launch {
            viewModel.words.collectLatest {
                bookmarkedWordsAdapter.submitData(it)
            }
        }
        return binding.root
    }
}