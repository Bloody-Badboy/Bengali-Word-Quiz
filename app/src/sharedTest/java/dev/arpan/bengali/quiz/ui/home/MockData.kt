package dev.arpan.bengali.quiz

import dev.arpan.bengali.quiz.data.model.QuizWordItem
import dev.arpan.bengali.quiz.data.model.Word

val WORDS: List<Word> = listOf(
    Word(
        id = 1,
        bengali = "Foo1",
        english = "Foobar1",
        bookmarked = false
    ),
    Word(
        id = 2,
        bengali = "Foo2",
        english = "Foobar2",
        bookmarked = false
    ),
    Word(
        id = 3,
        bengali = "Foo3",
        english = "Foobar3",
        bookmarked = false
    ),
    Word(
        id = 4,
        bengali = "Foo4",
        english = "Foobar4",
        bookmarked = false
    ),
    Word(
        id = 5,
        bengali = "Foo5",
        english = "Foobar5",
        bookmarked = false
    )
)

val QUIZ_WORD_ITEM = QuizWordItem(
    wordId = 1,
    word = "Foobar",
    bookmarked = false,
    options = listOf(
        QuizWordItem.Option(option = "Option 1", correctOption = true),
        QuizWordItem.Option(option = "Option 2", correctOption = false),
        QuizWordItem.Option(option = "Option 3", correctOption = false),
        QuizWordItem.Option(option = "Option 4", correctOption = false)
    )
)

val QUIZ_WORD_ITEM_BOOKMARKED = QuizWordItem(
    wordId = 1,
    word = "Foobar",
    bookmarked = true,
    options = listOf(
        QuizWordItem.Option(option = "Option 1", correctOption = true),
        QuizWordItem.Option(option = "Option 1", correctOption = false),
        QuizWordItem.Option(option = "Option 1", correctOption = false),
        QuizWordItem.Option(option = "Option 1", correctOption = false)
    )
)