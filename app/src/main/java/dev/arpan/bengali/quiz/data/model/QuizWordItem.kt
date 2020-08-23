package dev.arpan.bengali.quiz.data.model

data class QuizWordItem(
    val wordId: Int,
    val word: String,
    val bookmarked: Boolean,
    val options: List<Option>
) {
    data class Option(
        val option: String,
        val correctOption: Boolean = false,
        var clickable: Boolean = true
    )
}