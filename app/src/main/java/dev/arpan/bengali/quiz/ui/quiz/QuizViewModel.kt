package dev.arpan.bengali.quiz.ui.quiz

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.arpan.bengali.quiz.data.WordsRepository
import dev.arpan.bengali.quiz.data.model.QuizWordItem
import dev.arpan.bengali.quiz.ui.utils.Event
import kotlinx.coroutines.launch

class QuizViewModel @ViewModelInject constructor(private val repository: WordsRepository) :
    ViewModel() {

    private val _quizWord = MutableLiveData<QuizWordItem>()
    val quizWord: LiveData<QuizWordItem>
        get() = _quizWord

    private val _isLastWord = MutableLiveData<Boolean>()
    val isLastWord: LiveData<Boolean>
        get() = _isLastWord

    private val _isBookmarked = MutableLiveData<Boolean>()
    val isBookmarked: LiveData<Boolean>
        get() = _isBookmarked

    private val _currentWordIndex = MutableLiveData<Int>()
    val currentWordIndex: LiveData<Int>
        get() = _currentWordIndex

    private val _toolbarTitle = MutableLiveData<String>()
    val toolbarTitle: LiveData<String>
        get() = _toolbarTitle

    private val _showFinishedDialog = MutableLiveData<Event<Unit>>()
    val showFinishedDialog: LiveData<Event<Unit>>
        get() = _showFinishedDialog

    private var questionIndex = 0
    var isEnglishToBengali = false
    var numberOfWords = 0
    var numberOfOptions = 0
    var currentScore = 0

    fun setToolbarTitle(title: String) {
        _toolbarTitle.value = title
    }

    fun onNextWordClick() {
        if (_isLastWord.value == true) {
            _showFinishedDialog.value = Event(Unit)
        } else {
            getQuestion()
        }
    }

    fun getQuestion() {
        viewModelScope.launch {
            repository.getQuizWord(isEnglishToBengali, numberOfOptions)?.let { quizWordItem ->
                questionIndex++

                _isBookmarked.value = quizWordItem.bookmarked
                _quizWord.value = quizWordItem
                _currentWordIndex.value = questionIndex
                if (questionIndex == numberOfWords) {
                    _isLastWord.value = true
                }
            }
        }
    }

    fun bookmarkWord(wordId: Int, bookmark: Boolean) {
        viewModelScope.launch {
            if (bookmark) {
                val success = repository.addWordToBookmark(wordId)
                if (success)
                    _isBookmarked.value = true
            } else {
                val success = repository.removeWordFromBookmark(wordId)
                if (success)
                    _isBookmarked.value = false
            }
        }
    }
}