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
