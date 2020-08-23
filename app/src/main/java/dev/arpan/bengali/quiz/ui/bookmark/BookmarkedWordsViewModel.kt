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