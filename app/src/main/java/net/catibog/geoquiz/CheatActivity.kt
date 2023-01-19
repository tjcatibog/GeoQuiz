package net.catibog.geoquiz

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import net.catibog.geoquiz.databinding.ActivityCheatBinding

private const val TAG = "net.catibog.geoquiz.CheatActivity"
private const val EXTRA_ANSWER_IS_TRUE = "net.catibog.geoquiz.answer_is_true"
const val EXTRA_ANSWER_SHOWN = "net.catibog.geoquiz.answer_shown"

class CheatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCheatBinding
    private val cheatViewModel: CheatViewModel by viewModels()
    private var answerIsTrue = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        answerIsTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)

        binding.showAnswerButton.setOnClickListener {
            cheatViewModel.shownResult = true
            displayCheatText()
            setAnswerShownResult()
        }
        binding.deviceRuntimeVersion.text = "API Level ${Build.VERSION.SDK_INT}"
        displayCheatText()
    }

    override fun onResume() {
        super.onResume()
        setAnswerShownResult()
    }

    private fun displayCheatText() {
        if (cheatViewModel.shownResult) {
            val answerText = when {
                answerIsTrue -> R.string.true_button
                else -> R.string.false_button
            }
            binding.answerTextView.setText(answerText)
        }
    }

    private fun setAnswerShownResult() {
        val data = Intent().apply {
            putExtra(EXTRA_ANSWER_SHOWN, cheatViewModel.shownResult)
        }
        setResult(Activity.RESULT_OK, data)
    }

    companion object {
        fun newIntent(packageContext: Context, answerIsTrue: Boolean): Intent {
            return Intent(packageContext, CheatActivity::class.java).apply {
                putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue)
            }
        }
    }
}