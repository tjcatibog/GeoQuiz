package net.catibog.geoquiz

import androidx.lifecycle.SavedStateHandle
import org.junit.Assert.*
import org.junit.Test

class QuizViewModelTest {
    @Test
    fun providesExpectedQuestionText() {
        val savedStateHandle = SavedStateHandle()
        val quizViewModel = QuizViewModel(savedStateHandle)
        assertEquals(R.string.question_australia, quizViewModel.currentQuestionText)
    }

    @Test
    fun wrapsAroundQuestionBank() {
        val savedStateHandle = SavedStateHandle(mapOf(CURRENT_INDEX_KEY to 5))
        val quizViewModel = QuizViewModel(savedStateHandle)
        assertEquals(R.string.question_asia, quizViewModel.currentQuestionText)
        quizViewModel.moveToNext()
        assertEquals(R.string.question_australia, quizViewModel.currentQuestionText)
    }

    @Test
    fun currentQuestionAnswerIsFalse() {
        val savedStateHandle = SavedStateHandle(mapOf(CURRENT_INDEX_KEY to 2))
        val quizViewModel = QuizViewModel(savedStateHandle)
        assertEquals(R.string.question_mideast, quizViewModel.currentQuestionText)
        assertFalse(quizViewModel.currentQuestionAnswer)
    }

    @Test
    fun currentQuestionAnswerIsTrue() {
        val savedStateHandle = SavedStateHandle(mapOf(CURRENT_INDEX_KEY to 5))
        val quizViewModel = QuizViewModel(savedStateHandle)
        assertEquals(R.string.question_asia, quizViewModel.currentQuestionText)
        assertTrue(quizViewModel.currentQuestionAnswer)
    }
}