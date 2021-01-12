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
import dev.arpan.bengali.quiz.ui.utils.updateBookmarkedWordsAppWidget
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BookmarkedWordsFragment : NavigationDestinationFragment() {

    private val viewModel: BookmarkedWordsViewModel by viewModels()
    private lateinit var binding: FragmentBookmarkedWordsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBookmarkedWordsBinding.inflate(inflater, container, false)

        val bookmarkedWordsAdapter = BookmarkedWordsAdapter()
        val itemTouchHelper = ItemTouchHelper(object : SwipeToDeleteCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val word = bookmarkedWordsAdapter.getWordAt(viewHolder.absoluteAdapterPosition)
                if (word != null) {
                    viewModel.bookmarkWord(word.id, false)
                    requireContext().updateBookmarkedWordsAppWidget()
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
