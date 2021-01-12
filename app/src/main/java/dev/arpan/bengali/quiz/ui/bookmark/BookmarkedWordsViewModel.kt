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

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dev.arpan.bengali.quiz.data.WordsRepository
import dev.arpan.bengali.quiz.data.model.Word
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import timber.log.Timber

class BookmarkedWordsViewModel @ViewModelInject constructor(private val repository: WordsRepository) :
    ViewModel() {

    val words: Flow<PagingData<Word>> = repository.bookmarkedWords().cachedIn(viewModelScope)

    fun bookmarkWord(wordId: Int, addToBookmark: Boolean) {
        viewModelScope.launch {
            if (addToBookmark) {
                val success = repository.addWordToBookmark(wordId)
                if (success) {
                    Timber.d("wordId: $wordId added to bookmark.")
                }
            } else {
                val success = repository.removeWordFromBookmark(wordId)
                if (success) {
                    Timber.d("wordId: $wordId removed from bookmark.")
                }
            }
        }
    }
}
