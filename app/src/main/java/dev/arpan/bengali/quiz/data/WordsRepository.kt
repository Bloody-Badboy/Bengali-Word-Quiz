package dev.arpan.bengali.quiz.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import dev.arpan.bengali.quiz.data.db.WordsDao
import dev.arpan.bengali.quiz.data.model.QuizWordItem
import dev.arpan.bengali.quiz.data.model.Word
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

interface WordsRepository {
    suspend fun getQuizWord(isEnglishToBengali: Boolean, optionCount: Int): QuizWordItem?

    suspend fun addWordToBookmark(wordId: Int): Boolean

    suspend fun removeWordFromBookmark(wordId: Int): Boolean

    fun bookmarkedWords(): Flow<PagingData<Word>>
}

class DefaultWordsRepository(
    private val wordsDao: WordsDao,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : WordsRepository {

    companion object {
        private const val PAGE_SIZE = 10
    }

    override suspend fun getQuizWord(isEnglishToBengali: Boolean, optionCount: Int): QuizWordItem? {
        val wordList = withContext(dispatcher) {
            wordsDao.selectRandomWords(optionCount)
        }
        if (wordList.isEmpty()) {
            return null
        }

        val firstWord = wordList[0]
        val options = mutableListOf<QuizWordItem.Option>().apply {
            wordList.forEachIndexed { index, word ->
                val correctOption = index == 0
                add(
                    QuizWordItem.Option(
                        option = if (isEnglishToBengali) word.bengali else word.english,
                        correctOption = correctOption
                    )
                )
            }
            shuffle()
        }
        return QuizWordItem(
            wordId = firstWord.id,
            word = if (isEnglishToBengali) firstWord.english else firstWord.bengali,
            bookmarked = firstWord.bookmarked,
            options = options
        )
    }

    override suspend fun addWordToBookmark(wordId: Int): Boolean {
        return withContext(dispatcher) {
            wordsDao.bookmarkWord(wordId, true) > 0
        }
    }

    override suspend fun removeWordFromBookmark(wordId: Int): Boolean {
        return withContext(dispatcher) {
            wordsDao.bookmarkWord(wordId, false) > 0
        }
    }

    override fun bookmarkedWords(): Flow<PagingData<Word>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = { wordsDao.bookmarkedWordList() }
        ).flow
    }
}