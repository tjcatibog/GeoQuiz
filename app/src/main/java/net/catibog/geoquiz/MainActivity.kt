package net.catibog.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import com.google.android.material.snackbar.Snackbar
import net.catibog.geoquiz.databinding.ActivityMainBinding

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val quizViewModel: QuizViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d(TAG, "Got a QuizViewModel: $quizViewModel")

        binding.trueButton.setOnClickListener { checkAnswer(true) }
        binding.falseButton.setOnClickListener { checkAnswer(false)}
        binding.nextButton.setOnClickListener { nextQuestion() }
        binding.prevButton.setOnClickListener { prevQuestion() }
        binding.questionTextView.setOnClickListener { nextQuestion() }

        updateQuestion()
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }

    private fun nextQuestion() {
        quizViewModel.moveToNext()
        updateQuestion()
    }

    private fun prevQuestion() {
        quizViewModel.moveToPrev()
        updateQuestion()
    }

    private fun updateQuestion() {
        binding.questionTextView.setText(quizViewModel.currentQuestionText)
        if (quizViewModel.currentQuestionAnswered) disableButtons() else enableButtons()
    }

    private fun enableButtons() {
        binding.trueButton.isEnabled = true
        binding.falseButton.isEnabled = true
    }

    private fun disableButtons() {
        binding.trueButton.isEnabled = false
        binding.falseButton.isEnabled = false
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val messageResId = quizViewModel.checkAnswer(userAnswer)
        Snackbar.make(binding.baseLayout, messageResId, Snackbar.LENGTH_SHORT).show()
        updateQuestion()

        if (quizViewModel.allQuestionsAnswered) {
            Toast.makeText(this, "Score: ${quizViewModel.score}", Toast.LENGTH_LONG).show()
        }
    }
}