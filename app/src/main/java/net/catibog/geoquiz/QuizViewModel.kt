package net.catibog.geoquiz

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

const val CURRENT_INDEX_KEY = "CURRENT_INDEX_KEY"
const val IS_CHEATER_KEY = "IS_CHEATER_KEY"

class QuizViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {
    private var correct = 0
    private var answered = 0
    private var currentIndex: Int
        get() = savedStateHandle[CURRENT_INDEX_KEY] ?: 0
        set(value) = savedStateHandle.set(CURRENT_INDEX_KEY, value)
    var isCheater: Boolean
        get() = savedStateHandle["CHEATED_QUESTION_${currentQuestionText}"] ?: false
        set(value) = savedStateHandle.set("CHEATED_QUESTION_${currentQuestionText}", value)

    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true),
    )

    private var questionsUserAnswers = questionBank.associateWith { false }.toMutableMap()

    val currentQuestionAnswer: Boolean
        get() = questionBank[currentIndex].answer
    val allQuestionsAnswered: Boolean
        get() = answered == questionBank.size
    val currentQuestionText: Int
        get() = questionBank[currentIndex].textResId
    val currentQuestionAnswered: Boolean
        get() = questionsUserAnswers[questionBank[currentIndex]]!!
    val score: String
        get() = "${correct}/${answered}"

    fun checkAnswer(userAnswer: Boolean) {
        questionsUserAnswers[questionBank[currentIndex]] = true
        answered++
        if (!isCheater && userAnswer == currentQuestionAnswer) {
            correct++
        }
    }

    fun moveToNext() {
        currentIndex = (currentIndex + 1) % questionBank.size
    }

    fun moveToPrev() {
        currentIndex = (currentIndex + questionBank.size - 1) % questionBank.size
    }
}